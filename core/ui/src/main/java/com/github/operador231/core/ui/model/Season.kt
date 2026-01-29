package com.github.operador231.core.ui.model

public enum class Season(public val value: Int) {
    UNKNOWN(-1),
    WINTER(0),
    SPRING(1),
    SUMMER(2),
    AUTUMN(3);

    public companion object {
        public fun getSeasonByMonth(month: Int?): Season {
            return when (month) {
                12, 1, 2 -> WINTER
                3, 4, 5 -> SPRING
                6, 7, 8 -> SUMMER
                9, 10, 11 -> AUTUMN
                else -> UNKNOWN
            }
        }
    }
}