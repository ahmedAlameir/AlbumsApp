package com.example.albumsapp.feature.album.uesCase

import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.dataModel.Photo
import com.example.albumsapp.feature.album.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SearchPhotosUseCase(private val photosRepository: PhotosRepository)   {
    private val cachedPhotos: MutableMap<Int, List<Photo>> = mutableMapOf()

    suspend fun searchPhotos(albumId: Int, query: String): Flow<ResultState<List<Photo>>> {
        val cachedResult = cachedPhotos[albumId]?.let { photos ->
            ResultState.Success(filterPhotos(photos, query))
        }

        return flow {
            emit(cachedResult ?: photosRepository.getPhotos(albumId)
                .map { resultState ->
                    if (resultState is ResultState.Success) {
                        val photos = resultState.data
                        cachedPhotos[albumId] = photos
                        ResultState.Success(filterPhotos(photos, query))
                    } else {
                        resultState // Pass through the error or loading state
                    }
                }
                .first()
            )
        }
    }

    private fun filterPhotos(photos: List<Photo>, query: String): List<Photo> {
        return if (query.isNotEmpty()) {
            photos.filter { it.title.contains(query, ignoreCase = true) }
        } else {
            photos
        }
    }
}
