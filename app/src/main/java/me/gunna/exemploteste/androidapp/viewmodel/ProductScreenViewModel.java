package me.gunna.exemploteste.androidapp.viewmodel;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by root on 05/08/17.
 */

public class ProductScreenViewModel extends  ViewModel {
    private final PublishSubject<Void> onClick = PublishSubject.create();

    public Observable<Void> getOnClick() {
        return onClick.asObservable();
    }

    public void onClickProduct(){
        onClick.onNext(null);
    }

    @Override
    public void destroy() {

    }
}
