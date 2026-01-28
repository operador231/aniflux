package com.github.operador231.core.navigation

import kotlinx.serialization.Serializable

public interface Route {

    @Serializable
    public data object StateScreen : Route
}
