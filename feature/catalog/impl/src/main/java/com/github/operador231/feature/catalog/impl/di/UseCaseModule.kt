package com.github.operador231.feature.catalog.impl.di

import com.github.operador231.core.domain.repository.AnimeRepository
import com.github.operador231.feature.catalog.impl.data.usecase.GetAnimeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public object UseCaseModule {

    @Provides
    @Singleton
    public fun provideGetAnimeUseCase(repository: AnimeRepository): GetAnimeUseCase {
        return GetAnimeUseCase(repository)
    }
}