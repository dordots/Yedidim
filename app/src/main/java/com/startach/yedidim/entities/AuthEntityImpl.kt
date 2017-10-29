package com.startach.yedidim.entities

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import io.reactivex.Single
import io.reactivex.SingleEmitter
import timber.log.Timber
import java.util.concurrent.TimeUnit


class AuthEntityImpl(val activity: Activity, val userRegistrationState: UserRegistrationStateEntity) : AuthEntity {
    companion object {

        val VERIFICATION_TIMEOUT_SEC = 120L
    }

    private lateinit var emitter: SingleEmitter<AuthState>

    private var verificationId: String? = null
    private var token: PhoneAuthProvider.ForceResendingToken? = null
    override fun isAuthenticated(): Single<Boolean> {
        return Single.fromCallable({
            val currentUser = FirebaseAuth.getInstance().currentUser
            currentUser?.run {
                Timber.d("Current User : name : $displayName phoneNumber : $phoneNumber UID :$uid")
            }
            return@fromCallable currentUser != null
        })
    }

    override fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun verifyPhoneNumber(phoneNum: String): Single<AuthState> {
        return userRegistrationState.isUserRegistered(phoneNum)
                .flatMap { isRegistered ->
                    if (isRegistered)
                        return@flatMap firebaseVerification(phoneNum)
                    else
                        return@flatMap Single.just(AuthState.UnregisteredUser)
                }

    }

    private fun firebaseVerification(phoneNum: String): Single<AuthState> {
        return Single.create<AuthState> {
            emitter = it

            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, VERIFICATION_TIMEOUT_SEC, TimeUnit.SECONDS, activity,
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            signIn(credential)
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            Timber.e(e, "verification failed")
                            emitter.onSuccess(AuthState.Failure)
                        }

                        override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                            this@AuthEntityImpl.verificationId = verificationId
                            this@AuthEntityImpl.token = token
                            Timber.d("code sent")
                            emitter.onSuccess(AuthState.CodeSent)
                        }
                    })
        }
    }

    override fun loginWithCode(code: String): Single<AuthState> {
        return Single.create<AuthState> {
            emitter = it
            if (verificationId == null) {
                val illegalStateException = IllegalStateException("loginWithCode must be invoked after receiving AuthState.CodeSent in verifyPhoneNumber")
                Timber.e(illegalStateException)
                emitter.onError(illegalStateException)
            } else {
                val credential = PhoneAuthProvider.getCredential(verificationId as String, code)
                signIn(credential)
            }
        }
    }

    private fun signIn(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(activity, { task ->
                    if (task.isSuccessful) {
                        Timber.d("signInWithCredential:success")
                        emitter.onSuccess(AuthState.Success)
                    } else {
                        emitter.onSuccess(AuthState.Failure)
                        Timber.e(task.exception, "signInWithCredential:failure")
                    }
                })
    }


}