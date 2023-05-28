package com.sopt.instagram.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.seonhwan.android.veloginmobile.BuildConfig.VELOG_AUTH_TOKEN
import org.seonhwan.android.veloginmobile.BuildConfig.VELOG_BASE_URL
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val CONTENT_TYPE = "Content-Type"
    private const val APPLICATION_JSON = "application/json"
    private const val X_AUTH_TOKEN = "X-AUTH-TOKEN"

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor =
        Interceptor { chain ->
            with(chain) {
                proceed(
                    request()
                        .newBuilder()
                        .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .addHeader(X_AUTH_TOKEN, VELOG_AUTH_TOKEN)
                        .build(),
                )
            }
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        interceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
            .build()

    @Provides
    @Singleton
    fun providesInstagramRetrofit(
        client: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(VELOG_BASE_URL)
            .addConverterFactory(
                Json.asConverterFactory(
                    APPLICATION_JSON.toMediaType(),
                ),
            )
            .client(client)
            .build()
}
