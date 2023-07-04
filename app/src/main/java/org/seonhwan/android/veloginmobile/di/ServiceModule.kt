package org.seonhwan.android.veloginmobile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.seonhwan.android.veloginmobile.data.remote.service.SubscribeService
import org.seonhwan.android.veloginmobile.data.remote.service.TagService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun tagService(retrofit: Retrofit): TagService =
        retrofit.create(TagService::class.java)

    @Provides
    @Singleton
    fun subscribeService(retrofit: Retrofit): SubscribeService =
        retrofit.create(SubscribeService::class.java)
}
