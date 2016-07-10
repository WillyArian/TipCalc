package edu.willy.android.tipcalc.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.willy.android.tipcalc.R;
import edu.willy.android.tipcalc.TipCalcApp;
import edu.willy.android.tipcalc.fragments.TipHistoryFragment;
import edu.willy.android.tipcalc.fragments.TipHistoryListFragmentListener;
import edu.willy.android.tipcalc.models.TipRecord;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;
    @Bind(R.id.textTip)
    TextView textTip;

    private TipHistoryListFragmentListener fragmentListener;

    private final static int TIP_STEP_CHANGE =1;
    private final static int DEFAULT_TIP_PERCENTEGE =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TipHistoryFragment  fragment =(TipHistoryFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragmentList);
        fragment.setRetainInstance(true);
        fragmentListener = (TipHistoryListFragmentListener) fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            about();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit(){
        Log.e(getLocalClassName(), "Click on Submmit");
        hideKeyBoard();
        String stringInputTotal = inputBill.getText().toString().trim();

        if (!stringInputTotal.isEmpty()){
            double total = Double.parseDouble(stringInputTotal);
            int tipPercentege = getTipPercentege();

            TipRecord tipRecord = new TipRecord();
            tipRecord.setBill(total);
            tipRecord.setTipPercentage(tipPercentege);
            tipRecord.setTimeStamp(new Date());

            String strTip = String.format(getString(R.string.global_message_tip),
                    tipRecord.getTip());

            fragmentListener.addToList(tipRecord);
            textTip.setVisibility(View.VISIBLE);
            textTip.setText(strTip);
        }
    }

    @OnClick (R.id.btnIncrease)
    public void handleClickIncrease(){
        hideKeyBoard();
        handleTipChange(TIP_STEP_CHANGE);
    }

    @OnClick (R.id.btnDecrease)
    public void handleClickDecrease(){
        hideKeyBoard();
        handleTipChange(-TIP_STEP_CHANGE);
    }

    @OnClick (R.id.btnClear)
    public void handleClickClear(){
       fragmentListener.clearList();
    }

    private void handleTipChange(int change) {
        int tipPercentage = getTipPercentege();
        tipPercentage +=  change;

        if(tipPercentage > 0){
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
    }
    public int getTipPercentege() {
        int tipPerecentege = DEFAULT_TIP_PERCENTEGE;
        String strInputTipPercentege = inputPercentage.getText().toString().trim();
        if (!strInputTipPercentege.isEmpty()){
            tipPerecentege = Integer.parseInt(strInputTipPercentege);
        }else{
            inputPercentage.setText(String.valueOf(tipPerecentege));
        }
        return  tipPerecentege;
    }

    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    inputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe){
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }

    private void about() {
        TipCalcApp app = (TipCalcApp) getApplication();
        String strUrl = app.getAboutUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(strUrl));
        startActivity(intent);
    }
}