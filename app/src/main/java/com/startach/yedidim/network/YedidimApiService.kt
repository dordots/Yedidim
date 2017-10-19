package com.startach.yedidim.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface YedidimApiService {

    @GET("phones/{phoneNum}.json")
    fun getRegistrationState(@Path("phoneNum") phoneNum: String): Single<String?>
}