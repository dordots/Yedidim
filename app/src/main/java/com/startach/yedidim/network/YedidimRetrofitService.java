package com.startach.yedidim.network;

import com.startach.yedidim.entities.SMSRequestResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yb34982 on 07/09/2017.
 */

public interface YedidimRetrofitService {
    @GET("sendsms")
    Observable<SMSRequestResponse> sendLoginSMS(
            @Query("phoneNumber") String phoneNumber
    );
}
