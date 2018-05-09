package com.app.mohamedgomaa.arabic_book_buy;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

 class CheckConnection_Internet{
    Context context;
    ConnectivityManager ConMng;

      CheckConnection_Internet(Context context) {
        this.context = context;
    }
     boolean IsConnection()
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