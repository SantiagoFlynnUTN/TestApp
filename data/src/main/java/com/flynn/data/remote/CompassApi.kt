package com.flynn.data.remote

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface CompassApi {

    @GET("about")
    suspend fun getContent(): Response<String>

    companion object {
        private const val TIMEOUT_SECS = 30L

        fun init(
        ): CompassApi {
            val client = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)


            return Retrofit.Builder()
                .baseUrl("https://www.compass.com/")
                .client(client.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(CompassApi::class.java)
        }
    }
}