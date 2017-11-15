package com.app.mohamedgomaa.arabic_book_buy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.m_RecyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView_dAdapter = new RecyclerViewAdapter(, MainActivity.this);
        recyclerView.setAdapter(recyclerView_dAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

        }
}
