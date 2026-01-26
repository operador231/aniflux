package com.github.operador231.core.navigation

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds

/**
 * Core navigation DI module that facilitates feature discovery.
 *
 * This module uses Multibindings to collect all implementations of [FeatureApi]
 * from various feature modules into a single [Set].
 * */
@Module
@InstallIn(SingletonComponent::class)
public interface NavigationModule {

    /**
     * Declares an empty multibound set for [FeatureApi].
     *
     * This allows the app to inject `Set<FeatureApi>` even if no features are
     * currently providing an implementation. Feature modules contribute to this
     * set using the `@IntoSet` annotation.
     *
     * @return The complete set of registered feature APIs.
     * @see [FeatureApi]
     * */
    @Multibinds
    @JvmSuppressWildcards
    public fun featureApiSet(): Set<FeatureApi>
}