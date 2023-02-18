package ar.com.westsoft.listening.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Providers {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context
}
