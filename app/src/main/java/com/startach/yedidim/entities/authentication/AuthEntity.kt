package com.startach.yedidim.entities.authentication

import io.reactivex.Single

interface AuthEntity {

    fun isAuthenticated(): Single<Boolean>

    /**
     * Verify phone num, in case when needed code will be sent to phone in SMS
     * and `AuthState.CodeSent` will be emitted, in this case use `AuthEntity.loginWithCode` to continue
     * @param
     */
    fun verifyPhoneNumber(phoneNum: String): Single<AuthState>

    fun firebaseVerificationRetry(phoneNum: String): Single<AuthState>

    fun loginWithCode(code: String): Single<AuthState>

    fun logout()
}

enum class AuthState {
    Success, CodeSent, Failure, UnregisteredUser
}