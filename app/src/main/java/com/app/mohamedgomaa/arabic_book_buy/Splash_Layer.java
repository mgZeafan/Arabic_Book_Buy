package com.app.mohamedgomaa.arabic_book_buy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class Splash_Layer extends AppCompatActivity {
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__layer);
        new Thread(){
            @Override
            public void run() {
                super
        Fabric.with(this, new Crashlytics()); .run();

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
