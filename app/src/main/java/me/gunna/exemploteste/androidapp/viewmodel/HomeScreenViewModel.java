package me.gunna.exemploteste.androidapp.viewmodel;


import me.gunna.exemploteste.androidapp.ui.fragments.BaseFragment;
import me.gunna.exemploteste.androidapp.ui.fragments.ChartFragment;
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


    private final BaseFragment[] mMenuFragments = {
            ChartFragment.newInstance(),
            ProductsFragment.newInstance()
    };

    public Observable<BaseFragment> getMenuOptionObservable(){return mMenuOptionSubject.asObservable();}

    public HomeScreenViewModel() {mMenuOptionSubject.onNext(mMenuFragments[FRAG_ID_CHART]);}

    public void onClickChart() {mMenuOptionSubject.onNext(mMenuFragments[FRAG_ID_CHART]);}

    public void onClickProducts() {mMenuOptionSubject.onNext(mMenuFragments[FRAG_ID_PRODUCT]);}

    public void onClickLogout()   {}



    @Override
    public void destroy() {

    }
}
