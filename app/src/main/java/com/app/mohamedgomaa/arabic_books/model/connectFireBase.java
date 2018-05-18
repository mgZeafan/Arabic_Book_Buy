package com.app.mohamedgomaa.arabic_books.model;

import android.content.Context;
import android.widget.Toast;

import com.app.mohamedgomaa.arabic_books.main.IMainView;
import com.app.mohamedgomaa.arabic_books.main.MainActivity;
import com.app.mohamedgomaa.arabic_books.main.MainActivityAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mohamed on 5/18/2018.
 */

public class connectFireBase {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Context context;
    final String ParentGuid="ListBook";
    public connectFireBase(IMainView mainView) {
    context= (Context) mainView;


    }
public void onAddChangeListenerOnBook(final MainActivityAdapter mainActivityAdapter)
{
    databaseReference.child(ParentGuid).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (MainActivity.Books.size() > 0) {
                MainActivity.Books.clear();
            }
            for (DataSnapshot PostSnapshot : dataSnapshot.getChildren()) {
                if (PostSnapshot.exists()) {
                    item item = PostSnapshot.getValue(item.class);
                    MainActivity.Books.add(item);
                }
            }
            mainActivityAdapter.notifyDataSetChanged();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });
}
    public void Inilaze() {
        FirebaseApp.initializeApp(context);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }
}
