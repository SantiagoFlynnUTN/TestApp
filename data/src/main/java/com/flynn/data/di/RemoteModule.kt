package com.flynn.data.di

import com.flynn.data.remote.CompassApi
import com.flynn.data.remote.NetworkDataSource
import com.flynn.data.remote.NetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    const val TIMEOUT_SECS = 30L
    const val BASE_URL = "https://www.compass.com/"

    @Singleton
    @Provides
    fun provideOkHttp(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECS, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): CompassApi = retrofit.create(CompassApi::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        api: CompassApi
    ): NetworkDataSource = NetworkDataSourceImpl(
        compassApi = api
    )
}