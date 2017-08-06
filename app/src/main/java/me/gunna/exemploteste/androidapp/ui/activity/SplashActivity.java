package me.gunna.exemploteste.androidapp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import me.gunna.exemploteste.androidapp.app.SampleApp;


/**
 * Created by root on 05/08/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            if(SampleApp.getsInstance().isLogged())
                MainActivity.start(this);
            else
                LoginActivity.start(this);
            finish();
        },3000);
    }
}
