package com.app.mohamedgomaa.arabic_book_buy;
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
import com.app.mohamedgomaa.arabic_book_buy.util.IabHelper;
import com.app.mohamedgomaa.arabic_book_buy.util.IabResult;
import com.app.mohamedgomaa.arabic_book_buy.util.Inventory;
import com.app.mohamedgomaa.arabic_book_buy.util.Purchase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    static  String ITEM_SKU="zeafan_2018";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.recycleView);
        String base64EncodedPublicKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkcGrKLtzEY+LW9cCC" +
                "xqETJuD5PrZBnSa5txXfO+WN4xwIPMuhqSVq/IhPsmnlh93JC6eVAlmIH4l3RDJkIdCPZllGM1wZ5NkwOqiOFqdW400Terj6Otiuj" +
                "jq0outuJn6QY2teTjckYdjO6EQn6IP8Rn919jUgBjar+s5dY4lbI09excSq3dm7Ia+tLKEa4iWg+ZgxkMBOU1D0XyMqeQc2+5sGGMp" +
                "MioxoMko2rOx80Qur4CHrbgu5wunlYCLe9NNe0XFLTFMisDQbVr+HH8c5v+LV8MSSOPfADF6C/ATyY0NwvO8INQdD6EyHefomshOmuAiCXr8cOyu7GNK8MjpLQIDAQAB";

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

        if(new CheckConnection_Internet(this).IsConnection()) {
            Iniliazation();
            AddEventListener();
        }else {
            Toast.makeText(this,getResources().getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();
        }
        itemAdapter = new ItemAdapter(Books, MainActivity.this);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "hello"+i, Toast.LENGTH_SHORT).show();
            }
        });
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v,final MotionEvent event) {
//                ITEM_SKU=Books.get(event.getActionIndex()).book_id;
//
//                View convertView = v;
//                final Button dwn =convertView.findViewById(R.id.download);
//                dwn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Random Rand = new Random();
//                        int Rndnum = Rand.nextInt(10000) + 1;
//                        mHelper.launchPurchaseFlow(MainActivity.this,ITEM_SKU, 10001,
//                                mPurchaseFinishedListener, "token-" + Rndnum);
//                    }
//                });
//                return false;
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
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
                    }
                }
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
                        mHelper.launchPurchaseFlow(MainActivity.this,ITEM_SKU, 10001,
                                mPurchaseFinishedListener, "MyPurchasetoken-");
    }
}
