package com.github.operador231.core.database.di

import android.content.Context
import androidx.room.Room
import com.github.operador231.core.database.AniFluxDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public object DatabaseModule {

    @Provides
    @Singleton
    public fun provideDatabase(
        @ApplicationContext context: Context
    ): AniFluxDatabase {
        return Room
            .databaseBuilder(
                context = context,
                klass = AniFluxDatabase::class.java,
                name = "aniflux.db"
            )
            .fallbackToDestructiveMigration(true) // todo: do not use this!
            .build()
    }
}