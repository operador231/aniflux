package com.github.operador231.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * A contract for integrating feature modules into the app.
 *
 * In a multi-module architecture, this interface serves as an entry point for
 * feature decoupling. The main module consumes implementation of this interface without having
 * direct dependencies on the feature's internal logic.
 *
 * Each implementation should be exposed via DI.
 * */
public interface FeatureApi {

    /**
     * Registers the feature's navigation destinations within the app's graph.
     *
     * This method is called during [androidx.navigation.NavHost] configuration.
     * Implementations should use [navGraphBuilder] extensions like `composable`
     * or `navigation` to define the feature's internal routes.
     *
     * @param navGraphBuilder The builder used to construct the feature's navigation graph.
     * @param navController The navigation controller used for navigation between destinations.
     * */
    public fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    )

    /**
     * Optional metadata for integrating the feature into top-level navigation
     * components (e.g. Bottom Navigation Bar or Navigation Drawer).
     *
     * If `null`, the feature remains 'hidden' from the global UI components and
     * is only accessible via direct navigation.
     *
     * @see [FeatureNavInfo]
     * */
    public val navInfo: FeatureNavInfo? get() = null
}