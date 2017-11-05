package com.startach.yedidim.entities.authentication

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single


sealed class AuthTokenProvider {

    companion object {
        fun getToken(): Single<String?> {
            return Single.create<String?> { e ->
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    user.getIdToken(true).addOnCompleteListener({
                        if (it.isSuccessful) {
                            e.onSuccess(if (it.result.token == null) "" else it.result.token!!)
                        } else {
                            it.exception?.let { e.onError(it) }
                        }
                    })
                } else {
                    e.onError(Exception("user not authenticated"))
                }
            }
        }
    }
}