package com.github.operador231.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
public object DispatcherModule {

    @Provides
    @IODispatcher
    public fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DefaultDispatcher
    public fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}