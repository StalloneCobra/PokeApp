package me.gunna.exemploteste.androidapp.viewmodel;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import me.gunna.exemploteste.androidapp.app.SampleApp;
import me.gunna.exemploteste.androidapp.ui.fragments.BaseFragment;
import me.gunna.exemploteste.androidapp.ui.fragments.ChartFragment;
import me.gunna.exemploteste.androidapp.ui.fragments.LoginFragment;
import me.gunna.exemploteste.androidapp.ui.fragments.ProductsFragment;
import rx.Observable;
import rx.subjects.ReplaySubject;


/**
 * Created by Daniel on 18/07/17.
 */

public class HomeScreenViewModel extends ViewModel {
    private final ReplaySubject<BaseFragment> mMenuOptionSubject = ReplaySubject.create();
    private static final int FRAG_ID_CHART =  0;
    private static final int FRAG_ID_PRODUCT =  1;
    private static final int FRAG_ID_LOGIN =  2;
    private final ObservableField<String> mUserName = new ObservableField<>();
    private final ObservableField<String> mUserPhoto = new ObservableField<>();

    private final BaseFragment[] mMenuFragments = {
            ChartFragment.newInstance(),
            ProductsFragment.newInstance(),
            LoginFragment.newInstance()
    };



    public Observable<BaseFragment> getMenuOptionObservable(){return mMenuOptionSubject.asObservable();}

    public HomeScreenViewModel() {
        if(SampleApp.getsInstance().getmIsLogged().get())
            mUserPhoto.set(getUserPhotoUrl());
        mMenuOptionSubject.onNext(mMenuFragments[FRAG_ID_CHART]);
    }

    private String getUserPhotoUrl() {
        return "http://graph.facebook.com/" +
                SampleApp.getsInstance().getmToken().get()
                + "/picture?type=large";
    }

    public void onClickChart() {mMenuOptionSubject.onNext(mMenuFragments[FRAG_ID_CHART]);}

    public void onClickProducts() {mMenuOptionSubject.onNext(mMenuFragments[FRAG_ID_PRODUCT]);}

    public void onClickLogout()   {mMenuOptionSubject.onNext(mMenuFragments[FRAG_ID_LOGIN]);}

    public ObservableField<String> getUserName(){
        return  mUserName;
    }

    public ObservableField<String> getUserPhoto(){
        return  mUserPhoto;
    }

    public ObservableBoolean getIsUserLogged(){
        return  SampleApp.getsInstance().getmIsLogged();
    }

    @Override
    public void destroy() {

    }
}
