package edu.willy.android.tipcalc;

import android.app.Application;

/**
 * Created by Z3r0x on 09/07/2016.
 */
public class TipCalcApp extends Application {
    private final static String ABOUT_URL= "http://www.google.com";

    public  String getAboutUrl() {
        return ABOUT_URL;
    }
}