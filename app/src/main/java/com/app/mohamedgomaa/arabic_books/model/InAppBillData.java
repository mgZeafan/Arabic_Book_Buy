package com.app.mohamedgomaa.arabic_books.model;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.app.mohamedgomaa.arabic_books.main.IMainView;
import com.app.mohamedgomaa.arabic_books.util.IabHelper;
import com.app.mohamedgomaa.arabic_books.util.IabResult;
import com.app.mohamedgomaa.arabic_books.util.Inventory;
import com.app.mohamedgomaa.arabic_books.util.Purchase;

/**
 * Created by mohamed on 5/18/2018.
 */

public  class InAppBillData {
    IabHelper mHelper;
    private String ITEM_SKU;
    public void RequestGetItem(IMainView mainView, String bookID) {
        mHelper.launchPurchaseFlow((Activity) mainView, bookID, 10001,
                mPurchaseFinishedListener, "mypurchasetoken");
        ITEM_SKU =bookID;
    }

    public boolean SendResult(int requestCode, int resultCode, Intent data) {
        return mHelper.handleActivityResult(requestCode,
                resultCode, data);
    }

    public void Destroy() {
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }

    public static class InfoFirebase{
        static final String TAG = "InAppBilling";
      static   String base64EncodedPublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgrsgMrTvm3582GL+ehKrwG4iVIAODZrfzFQllgWxEjfnzxcsUSiKSYFkS9xSpnO18u4wCGISijDOjieQJJ0cYNqPS5oMNzks1M+q0sGIrAHsYJ7zK+ui1ElrQlBhPp8vl4njCt99KDnoahhANJK6cLa0ZVn5Nz/5BNFlvzAcWpeVjvdnjz04VJ/kpiDVPKq12ENjIMoW3ujpgOHc3iyXu+Nr5e5glcEvrpVEPsXOY0wMiXXdm/8kAgg7O6dpi+lTarTuo3UAGlkamEKaBaI0uWz/m+y2LRgkpR3G9H0dOezthNashSSs8Aea/8OUvBiNqWfkdSkdklBJyL86vRh3kQIDAQAB";
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (result.isFailure()) {
                // Handle error
                return;
            } else if (purchase.getSku().equals(ITEM_SKU)) {
                consumeItem();
            }
        }
    };
    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener
            = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result,
                                             Inventory inventory) {

            if (result.isFailure()) {
                // Handle failure
            } else {
                mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU),
                        mConsumeFinishedListener);
            }
        }
    };
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
            new IabHelper.OnConsumeFinishedListener() {
                public void onConsumeFinished(Purchase purchase,
                                              IabResult result) {

                    if (result.isSuccess()) {

                    } else {

                    }
                }
            };
public void InilizeFireBase(Activity context) {
    mHelper = new IabHelper(context, InfoFirebase.base64EncodedPublicKey);
    mHelper.startSetup(new
                               IabHelper.OnIabSetupFinishedListener() {
                                   public void onIabSetupFinished(IabResult result) {
                                       if (!result.isSuccess()) {
                                           Log.d(InfoFirebase.TAG, "In-app Billing setup failed: " +
                                                   result);
                                       } else {
                                           Log.d(InfoFirebase.TAG, "In-app Billing is set up OK");
                                       }
                                   }
                               });
    context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
            , WindowManager.LayoutParams.FLAG_FULLSCREEN);
}
}
