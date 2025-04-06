package com.example.speerinterviewsubmission.data.network

import com.example.speerinterviewsubmission.data.network.services.UserServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Client {


    private val gson: Gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()

        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    fun <API> createApiEndPoint(
        api: Class<API>
    ): API =
        Retrofit.Builder()
            .baseUrl(Urls.BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(api)

    fun getUserServices() = createApiEndPoint(UserServices::class.java)


}