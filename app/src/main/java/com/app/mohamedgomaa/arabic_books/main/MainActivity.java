package com.app.mohamedgomaa.arabic_books.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.app.mohamedgomaa.arabic_books.R;
import com.app.mohamedgomaa.arabic_books.model.item;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMainView {
    ListView recyclerView;
    MainActivityAdapter mainActivityAdapter;
    static public ArrayList<item> Books = new ArrayList<>();
    IMainPresenter presenter;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycleView);
        presenter = new MainPresenter(this);
        presenter.ConnectionInAppBill();
        presenter.InilazeFireBaseData();
        mainActivityAdapter = new MainActivityAdapter(Books, MainActivity.this);
        presenter.GetDateFromFirebase(mainActivityAdapter);
        recyclerView.setAdapter(mainActivityAdapter);
        mainActivityAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if(! presenter.SendOnActivityResult(requestCode,
                resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
        }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.DestroymHelper();
    }
    @Override
    public void SendRequestToPresentet(String BookID) {
        presenter.SendRequestToInAppBill(BookID);
    }

    @Override
    public void startActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void ShowMsg(String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
}
