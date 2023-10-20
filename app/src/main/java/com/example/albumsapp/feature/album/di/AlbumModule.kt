package com.example.albumsapp.feature.album.di

import com.example.albumsapp.feature.album.repository.PhotosRepository
import com.example.albumsapp.feature.album.repository.PhotosRepositoryImpl
import com.example.albumsapp.feature.album.service.PhotosService
import com.example.albumsapp.feature.album.store.AlbumStore
import com.example.albumsapp.feature.album.uesCase.SearchPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlbumModule {
    @Provides
    @Singleton
    fun provideSharedId(): AlbumStore {
        return AlbumStore(null)
    }
    @Provides
    fun providePhotoService(retrofit: Retrofit): PhotosService {
        return retrofit.create(PhotosService::class.java)
    }
    @Provides
    fun providePhotoRepository(photosService: PhotosService): PhotosRepository {
        return PhotosRepositoryImpl(photosService)
    }
    @Provides
    fun  provideSearchPhotosUseCase(photosRepository:PhotosRepository):SearchPhotosUseCase{
        return SearchPhotosUseCase(photosRepository)
    }
}