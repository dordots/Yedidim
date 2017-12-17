package com.startach.yedidim.network

import com.startach.yedidim.Model.Event
import com.startach.yedidim.entities.usermanagement.UserManager
import io.reactivex.Completable
import io.reactivex.Single


class EventApi(private val userManager: UserManager,
               private val cloudFunctionsApiService: YedidimCloudFunctionsApiService, private val apiService: YedidimApiService) {

    companion object {
        private val STATUS_COMPLETED = "completed"
        private val STATUS_SENT = "sent"
    }

    fun takeEvent(eventKey: String): Single<Boolean> {
        return userManager.getCurrentUser()
                .flatMap { user ->
                    if (user.id == null) {
                        return@flatMap Single.error<Boolean>(Exception("User not logged in"))
                    } else
                        return@flatMap cloudFunctionsApiService.takeEvent(TakeEventRequest(eventKey, user.id))
                                .flatMap responseFlatMap@ { res ->
                                    when (res.code()) {
                                        200 -> return@responseFlatMap Single.just(true)
                                        400 -> return@responseFlatMap Single.just(false)
                                        else -> return@responseFlatMap Single.error<Boolean>(Exception(res.errorBody()?.string()))
                                    }
                                }
                }
    }

    fun completeEvent(eventKey: String): Completable {
        val completedEvent = Event(key = eventKey, status = STATUS_COMPLETED)
        return apiService.updateEvent(eventKey, completedEvent)
    }

    fun cancelEvent(eventKey: String): Completable {
        val cancelledEvent = Event(key = eventKey, status = STATUS_SENT)
        return apiService.updateEvent(eventKey, cancelledEvent)
    }
}
