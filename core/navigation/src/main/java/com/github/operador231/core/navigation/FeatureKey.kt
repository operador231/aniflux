package com.github.operador231.core.navigation

import dagger.MapKey
import kotlin.reflect.KClass

/**
 * A [MapKey] annotation used to associate a specific [FeatureApi] implementation
 * with its class type in a Hilt MultiBinding map.
 *
 * @param value The [KClass] of the feature implementation extending [FeatureApi].
 * */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
public annotation class FeatureKey(val value: KClass<out FeatureApi>)