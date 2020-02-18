package com.appsnipp.admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    public static String MyPREFERENCES = "PREFS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("STEP 1", "New Application for you.", R.drawable.hero, Color.parseColor("#00695c")));
        addSlide(AppIntroFragment.newInstance("STEP 2", "View Account Details and manage them easily.", R.drawable.header, Color.parseColor("#2B3A3D")));
        addSlide(AppIntroFragment.newInstance("STEP 3", "See your society from anywhere in your Hand!", R.drawable.account_icon, Color.parseColor("#2B3A3D")));
        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean("AppIntro", false);
        editor.commit();
        finish();
    }
}
