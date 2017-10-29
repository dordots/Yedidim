package com.startach.yedidim.modules.network

import com.startach.yedidim.entities.AuthTokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException

class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        if (isNonAuthorizationNeededRequest(chain)) {
            return chain.proceed(chain.request())
        }

        try {
            val token = AuthTokenProvider.getToken().blockingGet()
            val originalHttpUrl = chain.request().url()

            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("auth", token)
                    .build()

            val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
            return chain.proceed(request)
        } catch (e: Exception) {
            Timber.e(e)
            return chain.proceed(chain.request())
        }
    }

    //prevent 'phones' request from trying to get token in an ugly way
    private fun isNonAuthorizationNeededRequest(chain: Interceptor.Chain) = chain.request().url().pathSegments().contains("phones")
}