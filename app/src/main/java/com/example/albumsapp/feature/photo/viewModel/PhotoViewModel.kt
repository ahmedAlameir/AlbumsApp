package com.example.albumsapp.feature.photo.viewModel

import androidx.lifecycle.ViewModel
import com.example.albumsapp.core.error.AppException
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.dataModel.Photo
import com.example.albumsapp.feature.photo.store.PhotoStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class PhotoViewModel@Inject constructor(private val photoStore:PhotoStore): ViewModel() {
    private val _photoState = MutableStateFlow<ResultState<Photo>> (ResultState.Loading)
    val photoState: StateFlow<ResultState<Photo>> = _photoState

    init {
        getPhoto()
    }
    private fun getPhoto() {
        _photoState.value = photoStore.photo?.let {
            ResultState.Success(it)
        } ?: ResultState.Error(AppException.UserNotFoundException("User not found"))

    }


}