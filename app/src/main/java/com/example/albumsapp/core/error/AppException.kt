package com.example.albumsapp.core.error

sealed class AppException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class UserNotFoundException(message: String) : AppException(message)
    class NetworkException(message: String) : AppException(message)
}
