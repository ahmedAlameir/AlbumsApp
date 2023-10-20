package com.example.albumsapp.feature.profile.repository

import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.profile.dataModel.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserRepository{
    suspend fun getUser(userId: Int): Flow<ResultState<User>>
}