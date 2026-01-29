package com.github.operador231.feature.catalog.impl.di

import com.github.operador231.core.domain.repository.AnimeRepository
import com.github.operador231.feature.catalog.impl.data.repository.AnimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public interface RepositoryModule {

    @Binds
    @Singleton
    public fun bindAnimeRepository(impl: AnimeRepositoryImpl): AnimeRepository
}