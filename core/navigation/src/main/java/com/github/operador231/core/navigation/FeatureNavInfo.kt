package com.github.operador231.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Metadata used to automatically populate top-level navigation UI components.
 *
 * * This data class provides the necessary information for a feature to be represented
 * in visual elements like a Bottom Navigation Bar, Navigation Drawer, etc.
 * */
public data class FeatureNavInfo(
    /**
     * The unique [Route] destination associated with this feature.
     * Used as the target when the user interacts with the navigation item.
     * */
    val route: Route,

    /**
     * The display name of the feature, typically shown below an icon
     * or as a content description.
     * */
    @param:StringRes val labelResId: Int,

    /**
     * The outlined or default version of the icon,
     * typically used when the feature is in an unselected state.
     * */
    @param:DrawableRes val iconResId: Int,

    /**
     * The filled version of the icon, typically used to provide
     * visual feedback when the feature is currently active/selected.
     * */
    @param:DrawableRes val iconFillResId: Int,

    /**
     * Determines the placement of the navigation item within the UI.
     * * Based on this value, the item will be routed to the Bottom Navigation Bar,
     * the Navigation Drawer, or excluded from global navigation.
     */
    val priority: NavigationPriority,

    /**
     * The appearance order within the navigation suite.
     * Lower values appear first (left-to-right or top-to-bottom).
     */
    val order: Int = 0
)
