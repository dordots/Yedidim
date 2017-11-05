package com.startach.yedidim.entities.authentication

import com.startach.yedidim.network.YedidimApiService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import javax.inject.Inject

interface UserRegistrationStateEntity {
    fun isUserRegistered(phoneNum: String): Single<Boolean>
}

class UserRegistrationStateEntityImpl @Inject constructor(retrofitService: Retrofit) : UserRegistrationStateEntity {

    companion object {
        val STATE_NOT_REGISTERED = "0"
        val STATE_NOT_EXISTS = "null"
        val STATE_REGISTERED = "1"
    }

    private var service: YedidimApiService = retrofitService.create(YedidimApiService::class.java)

    override fun isUserRegistered(phoneNum: String): Single<Boolean> {
        return service.getRegistrationState(phoneNum)
                .onErrorReturn { STATE_NOT_EXISTS }
                .map { it == STATE_REGISTERED }
                .subscribeOn(Schedulers.io())
    }

}