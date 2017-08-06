package me.gunna.exemploteste.androidapp.service;

import me.gunna.exemploteste.androidapp.service.model.BtcHistoryResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Daniel on 18/07/17.
 */

public  interface ISampleAppService {

    @GET("close.json?currency=BRL")
    Observable<BtcHistoryResponse> getBtcHistory();


}
