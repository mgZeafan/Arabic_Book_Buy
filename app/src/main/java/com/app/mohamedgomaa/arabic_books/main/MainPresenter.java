package com.app.mohamedgomaa.arabic_books.main;

import android.app.Activity;
import android.content.Intent;

import com.app.mohamedgomaa.arabic_books.model.InAppBillData;
import com.app.mohamedgomaa.arabic_books.model.connectFireBase;

public class MainPresenter implements IMainPresenter {
    IMainView mainView;
    InAppBillData _InAppBillData;
    connectFireBase fireBaseConnect;


    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
        _InAppBillData =new InAppBillData();
        fireBaseConnect=new connectFireBase(mainView);
    }

    @Override
    public void ConnectionInAppBill() {
        _InAppBillData.InilizeFireBase((Activity)mainView);
    }

    @Override
    public void InilazeFireBaseData() { fireBaseConnect.Inilaze();}

    @Override
    public void GetDateFromFirebase(MainActivityAdapter mainActivityAdapter) {
        fireBaseConnect.onAddChangeListenerOnBook(mainActivityAdapter);
    }

    @Override
    public void SendRequestToInAppBill(String bookID) {
        _InAppBillData.RequestGetItem(mainView,bookID);
    }

    @Override
    public boolean SendOnActivityResult(int requestCode, int resultCode, Intent data) {
     return _InAppBillData.SendResult(requestCode, resultCode, data);
    }

    @Override
    public void DestroymHelper() {
        _InAppBillData.Destroy();
    }


}
