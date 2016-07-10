package edu.willy.android.tipcalc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.willy.android.tipcalc.R;

public class TipDetailActivity extends AppCompatActivity {

    @Bind(R.id.textBillTotal)
    TextView textBillTotal;
    @Bind(R.id.textTip)
    TextView textTip;
    @Bind(R.id.textTipStam)
    TextView textTipStam;

    public final static String TIP_KEY ="tip";
    public final static String DATE_KEY ="timestap";
    public final static String BILL_TOTAL_KEY ="total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String strTotal = String.format(getString(R.string.tipDetail_message_bill),
                intent.getDoubleExtra(BILL_TOTAL_KEY, 0D));

        String strTip = String.format(getString(R.string.global_message_tip),
                intent.getDoubleExtra(TIP_KEY, 0D));

        textBillTotal.setText(strTotal);
        textTip.setText(strTip);
        textTipStam.setText(intent.getStringExtra(DATE_KEY));
    }
}