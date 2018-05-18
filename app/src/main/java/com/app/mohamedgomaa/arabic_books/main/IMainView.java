package com.app.mohamedgomaa.arabic_books.main;

import android.content.Intent;

/**
 * Created by mohamed on 5/18/2018.
 */

public interface IMainView {
   void SendRequestToPresentet(String BookID);
    void startActivity(Intent intent);

    void ShowMsg(String s);
}
