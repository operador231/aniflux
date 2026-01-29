package com.github.operador231.core.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
public annotation class IODispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
public annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
public annotation class ApplicationScope