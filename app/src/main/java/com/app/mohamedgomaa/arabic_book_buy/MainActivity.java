package com.app.mohamedgomaa.arabic_book_buy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    final String ParentGuid="ListBook";
    ItemAdapter itemAdapter;
    ArrayList<item> Books=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        Fabric.with(this, new Crashlytics());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        if(new CheckConnection_Internet(this).IsConnection()) {
            Iniliazation();
            AddEventListener();
        }else {
            Toast.makeText(this,getResources().getString(R.string.errorInternet), Toast.LENGTH_SHORT).show();
        }
        itemAdapter = new ItemAdapter(Books, MainActivity.this);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
    }

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
    }
