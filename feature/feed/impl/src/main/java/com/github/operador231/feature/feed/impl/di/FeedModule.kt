package com.github.operador231.feature.feed.impl.di

import com.github.operador231.core.navigation.FeatureApi
import com.github.operador231.feature.feed.impl.FeatureFeed
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
public interface FeedModule {

    @Binds
    @IntoSet
    public fun bindFeatureFeed(impl: FeatureFeed): FeatureApi
}