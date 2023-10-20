package com.example.albumsapp.feature.profile.di

import com.example.albumsapp.feature.profile.repository.AlbumsRepository
import com.example.albumsapp.feature.profile.repository.AlbumsRepositoryImpl
import com.example.albumsapp.feature.profile.repository.UserRepository
import com.example.albumsapp.feature.profile.repository.UserRepositoryImpl
import com.example.albumsapp.feature.profile.service.AlbumsService
import com.example.albumsapp.feature.profile.service.UserService
import com.example.albumsapp.feature.profile.uescase.GetUserWithRandomIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
    @Provides
    fun provideAlbumsService(retrofit: Retrofit): AlbumsService {
        return retrofit.create(AlbumsService::class.java)
    }
    @Provides
    fun provideRepository(userService: UserService): UserRepository {
        return UserRepositoryImpl(userService)
    }
    @Provides
    fun provideAlbumsRepository(albumsService: AlbumsService): AlbumsRepository {
        return AlbumsRepositoryImpl(albumsService)
    }
    @Provides
    fun provideUseCase(userRepository: UserRepository): GetUserWithRandomIdUseCase {
        return GetUserWithRandomIdUseCase(userRepository)
    }
}