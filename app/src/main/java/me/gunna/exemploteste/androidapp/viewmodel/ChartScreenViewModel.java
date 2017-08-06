package me.gunna.exemploteste.androidapp.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;


import me.gunna.exemploteste.androidapp.helper.RxJavaUtils;
import me.gunna.exemploteste.androidapp.service.SampleAppService;
import me.gunna.exemploteste.androidapp.service.model.BtcHistoryResponse;
import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;

/**
 * Created by root on 05/08/17.
 */

public class ChartScreenViewModel extends ViewModel {

    private static final int ENTRY_COUNT = 7;
    private final PublishSubject<BarSet> mOnGetDataSet = PublishSubject.create();
    private final PublishSubject<Throwable> mOnError = PublishSubject.create();
    private final ObservableBoolean mIsLoading = new ObservableBoolean();
    private final ObservableField<String> mLastUpdate = new ObservableField<>();
    private Subscription mGetCharDataSubscription;
    private BtcHistoryResponse mResponse;

    public ChartScreenViewModel() {
        mLastUpdate.set(" ");
    }


    public Observable<Throwable> getOnErrorObservable() {
        return mOnError;
    }

    public Observable<BarSet> getOnGetDataSetObservable() {
        return mOnGetDataSet.asObservable();
    }

    public void onClickUpdate(){
        mResponse = null;
        getChartData();
    }



    public void getChartData() {
        if (mResponse == null) {
            mIsLoading.set(true);
            mGetCharDataSubscription = SampleAppService
                    .getInstance()
                    .getBtcHistory()
                    .compose(RxJavaUtils.applySchedulers())
                    .flatMap(btcHistoryResponse -> {
                        if (btcHistoryResponse == null
                                || btcHistoryResponse.getmHistory() == null)
                            return Observable.error(
                                    new Throwable("Ops! Algo deu errado :( !"));
                        return Observable.just(btcHistoryResponse);
                    })
                    .subscribe(
                            this::onGetData,
                            mOnError::onNext
                    );
        }else{
            onGetData(mResponse);
        }
    }

    public ObservableField<String> getLastUpdate() {
        return mLastUpdate;
    }

    private void onGetData(BtcHistoryResponse response) {
        mResponse = response;
        mIsLoading.set(false);

        mLastUpdate.set(
                String.format("Ultima atualização: %s",
                        response.getmTime().getmLastUpdate()));

        mOnGetDataSet.onNext(getDataSet(
                response.getDates(ENTRY_COUNT),
                response.getValues(ENTRY_COUNT)
        ));
    }

    private BarSet getDataSet(String[] dates, float[] values) {
        BarSet set = new BarSet();
        int x = 0;
        for(String l : dates){
            set.addBar(new Bar(dates[x],values[x]));
            x++;
        }
        return set;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    @Override
    public void destroy() {
        RxJavaUtils.checkUnsubscribe(mGetCharDataSubscription);
    }
}
