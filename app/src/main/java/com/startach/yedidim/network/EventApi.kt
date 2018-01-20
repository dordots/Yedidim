package com.startach.yedidim.network

import com.startach.yedidim.Model.Event
import com.startach.yedidim.entities.usermanagement.UserManager
import com.startach.yedidim.utils.empty
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class EventApi(private val userManager: UserManager,
               private val cloudFunctionsApiService: YedidimCloudFunctionsApiService, private val apiService: YedidimApiService) {

    companion object {
        private val STATUS_COMPLETED = "completed"
        private val STATUS_SENT = "sent"
    }

    fun takeEvent(eventKey: String): Single<Boolean> {
        return userManager.getCurrentUser()
                .flatMap { user ->
                    if (user.mobilePhone == null) {
                        return@flatMap Single.error<Boolean>(Exception("User not logged in"))
                    } else
                        return@flatMap cloudFunctionsApiService.takeEvent(TakeEventRequest(eventKey, "+972${user.mobilePhone.drop(1)}"))
                                .flatMap responseFlatMap@ { res ->
                                    when (res.code()) {
                                        200 -> return@responseFlatMap Single.just(true)
                                        400 -> return@responseFlatMap Single.just(false)
                                        else -> return@responseFlatMap Single.error<Boolean>(Exception(res.errorBody()?.string()))
                                    }
                                }
                }
                .subscribeOn(Schedulers.io())
    }

    fun closeEvent(eventKey: String): Completable {
        val completedEvent = Event(key = eventKey, status = STATUS_COMPLETED)
        return apiService.updateEvent(eventKey, completedEvent)
                .subscribeOn(Schedulers.io())
    }

    fun cancelEvent(eventKey: String): Completable {
        val cancelledEvent = Event(key = eventKey, status = STATUS_SENT, assignedTo = String.empty)
        return apiService.updateEvent(eventKey, cancelledEvent)
                .subscribeOn(Schedulers.io())
    }

    fun listOfEvents(): Single<Set<Event>>? {
        return cloudFunctionsApiService.getEventsList()
                .subscribeOn(Schedulers.io())
                .onErrorReturnItem(HashSet())
    }
}
