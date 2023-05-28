package com.sopt.instagram.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.seonhwan.android.veloginmobile.data.service.TagService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule{
    @Provides
    @Singleton
    fun tagService(retrofit: Retrofit): TagService =
        retrofit.create(TagService::class.java)
}
