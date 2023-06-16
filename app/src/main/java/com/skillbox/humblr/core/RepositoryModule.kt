package com.skillbox.humblr.core

import com.skillbox.humblr.core.Repository
import com.skillbox.humblr.core.RepositoryImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsRepository(
        repository: RepositoryImplementation
    ): Repository
}