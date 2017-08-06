package me.gunna.exemploteste.androidapp.app;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;

import io.fabric.sdk.android.Fabric;
import me.gunna.exemploteste.androidapp.R;
import me.gunna.exemploteste.androidapp.helper.PreferencesUtils;
import rx_activity_result.RxActivityResult;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Daniel on 18/07/17.
 */

public class SampleApp extends Application {
    private static SampleApp sInstance;
    private static PreferencesUtils mPref;
    private final ObservableField<String> mToken = new ObservableField<>();
    private final ObservableBoolean mIsLogged = new ObservableBoolean();


    public ObservableField<String> getmToken() {
        return mToken;
    }

    public ObservableBoolean getmIsLogged() {
        return mIsLogged;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        RxActivityResult.register(this);
        RxPaparazzo.register(this);
        setUpDefaultFont();
        sInstance = this;
        Fabric.with(this, new Crashlytics());
        PreferencesUtils.initializeInstance(this);
        mPref = PreferencesUtils.getInstance();
        verifyLogin();
    }

    private void verifyLogin() {
        mIsLogged.set(isLogged());
        if(isLogged())
            mToken.set(getUserToken());
    }

    public static SampleApp getsInstance() {
        return sInstance;
    }

    public boolean isLogged(){
        return TextUtils.isEmpty(mPref
                .getString(Constants.SharedPreferences.USER_TOKEN));
    }

    public String getUserToken(){
        return mPref
                .getString(Constants.SharedPreferences.USER_TOKEN);
    }
    private void setUpDefaultFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.montserrat_regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public void loginUser(LoginResult loginResult) {
        Log.d("SampleApp","Login Sucess "+
                loginResult.getAccessToken().toString());

        mPref.setValue(Constants.SharedPreferences.USER_TOKEN,
                loginResult.getAccessToken().toString());
    }
}
