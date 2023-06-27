package com.sp0ort365.mawt

import android.app.Application
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import com.sp0ort365.mawt.local.AppDatabase
import com.sp0ort365.mawt.remote.ApiService
import com.sp0ort365.mawt.remote.NewsApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MostApp : Application() {

    companion object {
        private var _newsApi: NewsApiService? = null
        val newsApi: NewsApiService by lazy { _newsApi!! }

        private var _api: ApiService? = null
        val api: ApiService by lazy { _api!! }

        lateinit var db : AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        val gson = GsonBuilder().setLenient().create()
        val newsClient = initAndGetHttpClient()
        val betClient = initAndGetHttpClient()
        newsClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            chain.proceed(requestBuilder.build())
        })

        betClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Package","KlUet6y43te8Jg6G9bkDxN36f9X9ZiTkm")
            chain.proceed(requestBuilder.build())
        })

        val newsRetrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://graphql.theathletic.com/")
            .client(newsClient.build())
            .build()
        _newsApi = newsRetrofit.create(NewsApiService::class.java)

        val betsRetrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://betapilive.com/")
            .client(betClient.build())
            .build()
        _api = betsRetrofit.create(ApiService::class.java)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java,"bets_database").build()
    }


    private fun initAndGetHttpClient() :OkHttpClient.Builder {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        if (com.sp0ort365.mawt.BuildConfig.DEBUG)
            client.addInterceptor(logging)
        client.connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
        return client
    }
}
