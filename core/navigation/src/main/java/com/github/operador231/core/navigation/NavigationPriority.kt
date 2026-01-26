package com.github.operador231.core.navigation

/**
 * Represents the priority of a navigation item within the global navigation system.
 * * This priority determines where a feature is surfaced in the UI (e.g., Bottom Bar vs. Drawer).
 *
 * @see [FeatureNavInfo]
 */
public enum class NavigationPriority {
    /**
     * The highest priority.
     * Items with this priority are displayed in the **Bottom Navigation Bar**.
     */
    PRIMARY,

    /**
     * The secondary priority.
     * Items with this priority are displayed in the **Navigation Drawer**.
     */
    SECONDARY,

    /**
     * The lowest priority.
     * Items with this priority are excluded from global navigation menus,
     * even if they possess valid [FeatureNavInfo].
     */
    UNSPECIFIED;

    /**
     * Represents a weight associated with each [NavigationPriority].
     * */
    public val weight: Int get() = when(this) {
        PRIMARY -> 0
        SECONDARY -> 1
        UNSPECIFIED -> 2
    }
}