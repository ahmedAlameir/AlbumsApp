package com.example.albumsapp.feature.album.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.dataModel.Photo
import com.example.albumsapp.feature.album.store.AlbumStore
import com.example.albumsapp.feature.album.uesCase.SearchPhotosUseCase
import com.example.albumsapp.feature.photo.store.PhotoStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel@Inject constructor(private val searchPhotosUseCase : SearchPhotosUseCase,
                                        private val albumStore: AlbumStore,
                                        private val photoStore: PhotoStore
) : ViewModel() {
    private val _photoState = MutableStateFlow<ResultState<List<Photo>>> (ResultState.Loading)
    val photoState: StateFlow<ResultState<List<Photo>>> = _photoState
    init {
        fetchPhoto("")
    }

     fun fetchPhoto(query:String) {
        viewModelScope.launch {
            _photoState.value = ResultState.Loading
            albumStore.album?.id?.let {
                searchPhotosUseCase.searchPhotos(it, query).collect { photoResult ->
                    _photoState.value = photoResult

                }
            }

        }
    }
    fun storeImage(photo: Photo){
        photoStore.photo = photo
    }
    fun getAlbumName():String{
        return albumStore.album?.title ?: ""
    }
}