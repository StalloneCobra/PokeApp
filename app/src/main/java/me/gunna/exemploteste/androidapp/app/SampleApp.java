package me.gunna.exemploteste.androidapp.app;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;

import me.gunna.exemploteste.androidapp.R;
import rx_activity_result.RxActivityResult;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Daniel on 18/07/17.
 */

public class SampleApp extends Application {
    private static SampleApp sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        RxActivityResult.register(this);
        RxPaparazzo.register(this);
        setUpDefaultFont();
        sInstance = this;
    }

    public static SampleApp getsInstance() {
        return sInstance;
    }

    public boolean isLogged(){
        return true;
    }
    private void setUpDefaultFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.montserrat_regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
