package com.example.albumsapp.feature.photo.di

import com.example.albumsapp.feature.photo.store.PhotoStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule{
    @Provides
    @Singleton
    fun providePhotoStore(): PhotoStore {
        return PhotoStore(photo = null)
    }
}