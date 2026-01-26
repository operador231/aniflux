package com.github.operador231.feature.catalog.impl.di

import com.github.operador231.core.navigation.FeatureApi
import com.github.operador231.feature.catalog.impl.FeatureCatalog
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
public interface CatalogModule {

    @Binds
    @IntoSet
    public fun bindCatalog(impl: FeatureCatalog): FeatureApi
}