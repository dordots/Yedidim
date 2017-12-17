package com.startach.yedidim.network

import com.google.firebase.database.*
import com.startach.yedidim.Model.Event


class EventApi {

    fun takeEvent(event: Event) {
        val database = FirebaseDatabase.getInstance().reference
        val eventRef = database.child("events").child(event.key)

        eventRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val event = mutableData.getValue(Event::class.java) ?: return Transaction.success(mutableData)

                return if (event.status != "assigned") {
                    mutableData.value = event.copy(status = "assigned")
                    Transaction.success(mutableData)
                } else {
                    Transaction.abort()
                }
            }

            override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {

            }

        })
    }
}