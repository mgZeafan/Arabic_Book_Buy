package com.app.mohamedgomaa.arabic_books;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
public class Splash_Layer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__layer);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                        startActivity(new Intent(Splash_Layer.this, MainActivity.class));
                        finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
