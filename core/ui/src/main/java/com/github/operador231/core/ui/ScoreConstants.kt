package com.github.operador231.core.ui

/**
 * Thresholds used to categorize media scores and determine UI styling.
 *
 * These values define the minimum score required to reach a specific tier.
 * For example, a score must be >= [EXCELLENT] to be styled with the excellent color.
 *
 * Note: These values are calibrated for a 0.0 to 10.0 scale.
 */
public object ScoreConstants {
    /** Minimum score for the 'Excellent' tier (Top-tier content). */
    public const val EXCELLENT: Float = 8.5f

    /** Minimum score for the 'Good' tier. */
    public const val GOOD: Float = 7.5f

    /** Minimum score for the 'Normal' tier (Average content). */
    public const val NORMAL: Float = 6.0f

    /** Minimum score for the 'Bad' tier. */
    public const val BAD: Float = 3.0f

    /** * Minimum score for the 'Worst' tier.
     * Scores below this but above 0.0 are typically treated as unrated or critical failures.
     */
    public const val WORST: Float = 0.1f
}