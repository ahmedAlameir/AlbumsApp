package com.example.albumsapp.core.result


sealed class ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Throwable) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()

}