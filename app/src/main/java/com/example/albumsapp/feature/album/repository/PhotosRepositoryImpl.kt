package com.example.albumsapp.feature.album.repository

import com.example.albumsapp.core.error.AppException
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.dataModel.Photo
import com.example.albumsapp.feature.album.service.PhotosService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PhotosRepositoryImpl(private val photosService: PhotosService) : PhotosRepository {
    override suspend fun getPhotos(albumId: Int): Flow<ResultState<List<Photo>>> = flow {
        try {
            val response = photosService.getPhotosForAlbum(albumId)
            if (response.isSuccessful) {
                val photos = response.body()
                if (photos != null)  {
                    emit(ResultState.Success(photos))
                } else {
                    emit(ResultState.Error(AppException.UserNotFoundException("User not found")))
                }
            } else {
                emit(ResultState.Error(AppException.NetworkException("Error fetching user")))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}