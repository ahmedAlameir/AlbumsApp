package com.example.albumsapp.feature.album.repository

import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.dataModel.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    suspend fun getPhotos(albumId: Int): Flow<ResultState<List<Photo>>>

}