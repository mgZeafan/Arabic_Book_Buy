package com.app.mohamedgomaa.arabic_books;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.app.mohamedgomaa.arabic_books.util.IabHelper;
import com.app.mohamedgomaa.arabic_books.util.IabResult;
import com.app.mohamedgomaa.arabic_books.util.Inventory;
import com.app.mohamedgomaa.arabic_books.util.Purchase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.app.mohamedgomaa.arabic_books.util.*;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ListView recyclerView;
    private static final String TAG = "InAppBilling";
    IabHelper mHelper;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    final String ParentGuid="ListBook";
    ItemAdapter itemAdapter;
    ArrayList<item> Books=new ArrayList<>();
    static  String ITEM_SKU="android.zeafan.test_2018";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.recycleView);
        String base64EncodedPublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgrsgMrTvm3582GL+ehKrwG4iVIAODZrfzFQllgWxEjfnzxcsUSiKSYFkS9xSpnO18u4wCGISijDOjieQJJ0cYNqPS5oMNzks1M+q0sGIrAHsYJ7zK+ui1ElrQlBhPp8vl4njCt99KDnoahhANJK6cLa0ZVn5Nz/5BNFlvzAcWpeVjvdnjz04VJ/kpiDVPKq12ENjIMoW3ujpgOHc3iyXu+Nr5e5glcEvrpVEPsXOY0wMiXXdm/8kAgg7O6dpi+lTarTuo3UAGlkamEKaBaI0uWz/m+y2LRgkpR3G9H0dOezthNashSSs8Aea/8OUvBiNqWfkdSkdklBJyL86vRh3kQIDAQAB";
        try {
            mHelper = new IabHelper(this, base64EncodedPublicKey);
            mHelper.startSetup(new
                                       IabHelper.OnIabSetupFinishedListener() {
                                           public void onIabSetupFinished(IabResult result) {
                                               if (!result.isSuccess()) {
                                                   Log.d(TAG, "In-app Billing setup failed: " +
                                                           result);
                                               } else {
                                                   Log.d(TAG, "In-app Billing is set up OK");
                                               }
                                           }
                                       });
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    , WindowManager.LayoutParams.FLAG_FULLSCREEN);

            if (new CheckConnection_Internet(this).IsConnection()) {
                Iniliazation();
                AddEventListener();
            } else {
                Toast.makeText(this, getResources().getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();
            }
            itemAdapter = new ItemAdapter(Books, MainActivity.this);
            recyclerView.setAdapter(itemAdapter);
            itemAdapter.notifyDataSetChanged();
            recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    item item = (item) adapterView.getSelectedItem();

                    Toast.makeText(MainActivity.this, "hello" + i, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception ex)
        {
            Toast.makeText(this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
            FirebaseCrash.report(ex);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
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
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                        // handle error
                    }}
            };
    private void AddEventListener() {
        try {
            databaseReference.child(ParentGuid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (Books.size() > 0) {
                        Books.clear();
                    }
                    for (DataSnapshot PostSnapshot : dataSnapshot.getChildren()) {

                        item item = PostSnapshot.getValue(item.class);
                        Books.add(item);
                    }
                    itemAdapter = new ItemAdapter(Books, MainActivity.this);
                    recyclerView.setAdapter(itemAdapter);
                    itemAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }
    private void Iniliazation() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }
    public void Download(View view) {
        ITEM_SKU=Books.get(0).book_id;
                        mHelper.launchPurchaseFlow(this,ITEM_SKU, 10001,
                                mPurchaseFinishedListener, "mypurchasetoken");
    }
}
