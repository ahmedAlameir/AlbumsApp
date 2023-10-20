package com.example.albumsapp.feature.profile.service

import com.example.albumsapp.feature.profile.dataModel.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsService {
    @GET("/albums")
    suspend fun getAlbums(@Query("userId")id: Int): Response<List<Album>>
}