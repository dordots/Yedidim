package com.startach.yedidim.entities.usermanagement

import com.startach.yedidim.network.VolunteerApi
import io.reactivex.Completable


class VolunteerLocationUpdater(private val userManager: UserManager, private val volunteerApi: VolunteerApi) {

    fun updateLocation(latitude: Double, longitude: Double): Completable {
        return userManager.getCurrentUser()
                .flatMapCompletable {
                    val id = it.id ?: throw Exception("current user id is null!")
                    return@flatMapCompletable volunteerApi.updateVolunteerLocation(id, latitude, longitude)
                }
    }

}