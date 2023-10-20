package com.example.albumsapp.feature.profile.repository

import com.example.albumsapp.core.error.AppException
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.profile.dataModel.User
import com.example.albumsapp.feature.profile.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun getUser(userId: Int): Flow<ResultState<User>> = flow {
        try {
            val response = userService.getUser(userId)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    emit(ResultState.Success(user))
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

