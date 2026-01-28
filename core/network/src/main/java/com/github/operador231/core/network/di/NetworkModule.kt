package com.github.operador231.core.network.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.github.operador231.core.domain.network.NetworkMonitor
import com.github.operador231.core.network.BuildConfig
import com.github.operador231.core.network.NetworkMonitorImpl
import com.github.operador231.core.network.interceptor.AppInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public interface NetworkModule {

    @Binds
    @Singleton
    public fun bindNetworkMonitor(impl: NetworkMonitorImpl): NetworkMonitor

    public companion object {
        @Provides
        @Singleton
        public fun provideJson(): Json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = true
        }

        @Provides
        @Singleton
        @Named("ApiClient")
        public fun provideApiClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .addInterceptor(AppInterceptor())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES))
                .retryOnConnectionFailure(true)

            return builder.build()
        }

        @Provides
        @Singleton
        @Named("ImageClient")
        public fun provideImageClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor { chain ->
                    val request = chain.request()
                    var response: Response? = null
                    var closeCounter = 0
                    val maxRetries = 3

                    while (response == null || (!response.isSuccessful && closeCounter < maxRetries)) {
                        try {
                            response?.close()
                            response = chain.proceed(request)
                        } catch (ex: Exception) {
                            if (closeCounter >= maxRetries) throw ex
                        }
                        closeCounter++
                    }

                    response
                }
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.HEADERS
                    else HttpLoggingInterceptor.Level.NONE
                })
                .build()
        }

        @Provides
        @Singleton
        public fun provideApolloClient(
            @Named("ApiClient") okHttpClient: OkHttpClient
        ): ApolloClient {
            return ApolloClient.Builder()
                .serverUrl("https://shikimori.one/api/graphql") // todo: move to BuildConfig
                .okHttpClient(okHttpClient)
                .build()
        }
    }
}

