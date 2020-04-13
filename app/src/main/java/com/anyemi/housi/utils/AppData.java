package com.anyemi.housi.utils;

import android.app.Application;


import java.util.ArrayList;


public class AppData extends Application {
    public final static boolean IS_DEBUGGING_ON = false;



    public static boolean isIsDebuggingOn() {
        return IS_DEBUGGING_ON;
    }

    @Override
    public void onCreate() {

        // TODO Auto-generated method stub
        super.onCreate();


    }




}