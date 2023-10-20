package com.example.albumsapp.feature.profile.service

import com.example.albumsapp.feature.profile.dataModel.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>
}