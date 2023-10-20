package com.example.albumsapp.feature.profile.uescase

import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.profile.dataModel.User
import com.example.albumsapp.feature.profile.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserWithRandomIdUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): Flow<ResultState<User>> {
        val randomId = (1..10).random() // Generate a random ID between 1 and 10
        return userRepository.getUser(randomId)
    }
}
