package com.github.operador231.aniflux

import android.app.Application
import androidx.appcompat.content.res.AppCompatResources
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.asImage
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
public class AniFluxApplication : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }

    @Inject
    @Named("ImageClient")
    public lateinit var okHttpClient: OkHttpClient

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        val placeholderImage = AppCompatResources.getDrawable(
            context,
            com.github.operador231.core.ui.R.drawable.poster_placeholder
        )?.asImage()
        return ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory(
                    callFactory = { okHttpClient }
                ))
            }
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(1024L * 1024 * 1024)
                    .build()
            }
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .networkCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .error(placeholderImage)
            .fallback(placeholderImage)
            .build()
    }
}