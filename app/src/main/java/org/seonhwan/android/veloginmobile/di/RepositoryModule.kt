package org.seonhwan.android.veloginmobile.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.seonhwan.android.veloginmobile.data.remote.repository.SubscribeRepositoryImpl
import org.seonhwan.android.veloginmobile.data.remote.repository.TagRepositoryImpl
import org.seonhwan.android.veloginmobile.domain.repository.remote.SubscribeRepository
import org.seonhwan.android.veloginmobile.domain.repository.remote.TagRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun tagRepository(
        tagRepositoryImpl: TagRepositoryImpl,
    ): TagRepository

    @Binds
    @Singleton
    abstract fun subscribeRepository(
        subscribeRepositoryImpl: SubscribeRepositoryImpl,
    ): SubscribeRepository
}
