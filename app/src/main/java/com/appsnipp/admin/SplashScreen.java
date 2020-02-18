package com.appsnipp.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread splash=new Thread(){

            @Override
            public void run() {
                try {
                    sleep(1500);

                    Intent sp =new Intent(SplashScreen.this,LoginActivity.class);
                    startActivity(sp);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        splash.start();
    }
}
