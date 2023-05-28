package com.sopt.instagram.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.seonhwan.android.veloginmobile.data.repository.TagRepositoryImpl
import org.seonhwan.android.veloginmobile.domain.repository.TagRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun tagRepository(
        tagRepositoryImpl: TagRepositoryImpl,
    ): TagRepository
}
