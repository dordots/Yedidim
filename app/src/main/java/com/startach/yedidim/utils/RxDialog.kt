package com.startach.yedidim.utils

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import io.reactivex.Observable
import io.reactivex.Single


fun rxDialog(context: Context, @StringRes title: Int, @StringRes message: Int): Single<Boolean> {
    return Single.create {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, { dialog, which ->
                    dialog.dismiss()
                    it.onSuccess(true)
                })
                .setNegativeButton(android.R.string.cancel, { dialog, which ->
                    dialog.cancel()
                    it.onSuccess(false)
                })
                .show()
    }
}

fun <T> Observable<T>.showDialog(context: Context, @StringRes title: Int, @StringRes message: Int): Observable<T> {
    return this
            .compose { upstream ->
                upstream.flatMap { any ->
                    rxDialog(context, title, message)
                            .flatMapObservable {
                                when (it) {
                                    true -> Observable.just(any)
                                    false -> Observable.empty()
                                }
                            }
                }
            }
}
