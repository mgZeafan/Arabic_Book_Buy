package com.app.mohamedgomaa.arabic_books.main;

import android.content.Intent;

/**
 * Created by mohamed on 5/18/2018.
 */

public interface IMainPresenter {
   void ConnectionInAppBill();
   void InilazeFireBaseData();

    void GetDateFromFirebase(MainActivityAdapter mainActivityAdapter);

    void SendRequestToInAppBill(String bookID);

    boolean SendOnActivityResult(int requestCode, int resultCode, Intent data);
    public void DestroymHelper();
}
