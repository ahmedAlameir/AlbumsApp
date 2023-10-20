package com.example.albumsapp.feature.album.service

import com.example.albumsapp.feature.album.dataModel.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosService {
    @GET("photos")
    suspend fun getPhotosForAlbum(@Query("albumId") albumId: Int): Response<List<Photo>>
}