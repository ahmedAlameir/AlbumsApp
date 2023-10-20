package com.example.albumsapp.feature.profile.repository

import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.profile.dataModel.Album
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun getAlbums(userId: Int): Flow<ResultState<List<Album>>>

}