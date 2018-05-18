package com.app.mohamedgomaa.arabic_books.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

 public class CheckConnection_Internet{
    Context context;
    ConnectivityManager ConMng;

     public CheckConnection_Internet(Context context) {
        this.context = context;
    }
    public boolean IsConnection()
    {
        ConMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (ConMng != null){
            NetworkInfo info=ConMng.getActiveNetworkInfo();
            if(info!=null && info.isConnected())
            {
                return true;
            }
        }
        return false;
    }
}