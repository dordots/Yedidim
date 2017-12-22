package com.startach.yedidim.modules.network

import com.startach.yedidim.BuildConfig
import com.startach.yedidim.network.YedidimApiService
import com.startach.yedidim.network.YedidimCloudFunctionsApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        val CONNECTION_TIMEOUT_SEC = 10L
        val BASE_URL = "https://yedidim-sandbox-2.firebaseio.com/"
        val BASE_URL_CLOUD_FUNCTIONS = " https://us-central1-yedidim-sandbox-2.cloudfunctions.net/"
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return createRetrofit(BASE_URL)
    }

    @Singleton
    @Provides
    @Named("cloud_functions")
    fun providesCloudFunctionsRetrofit(): Retrofit {
        return createRetrofit(BASE_URL_CLOUD_FUNCTIONS)
    }

    @Singleton
    @Provides
    fun providesCloudFunctionsService(): YedidimCloudFunctionsApiService {
        return createRetrofit(BASE_URL_CLOUD_FUNCTIONS)
                .create(YedidimCloudFunctionsApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesYedidimService(): YedidimApiService {
        return createRetrofit(BASE_URL)
                .create(YedidimApiService::class.java)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT_SEC, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(interceptor)
        }
        clientBuilder.addInterceptor(AuthInterceptor())

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ToStringConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}