package me.gunna.exemploteste.androidapp.ui.activity;

import android.content.Context;
import android.content.Intent;

/**
 * Created by root on 05/08/17.
 */

public class LoginActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
}
