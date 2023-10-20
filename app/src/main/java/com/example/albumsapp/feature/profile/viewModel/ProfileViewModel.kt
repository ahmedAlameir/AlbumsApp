package com.example.albumsapp.feature.profile.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.store.AlbumStore
import com.example.albumsapp.feature.profile.dataModel.Album
import com.example.albumsapp.feature.profile.dataModel.User
import com.example.albumsapp.feature.profile.repository.AlbumsRepository
import com.example.albumsapp.feature.profile.uescase.GetUserWithRandomIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor( private val getUserWithRandomIdUseCase: GetUserWithRandomIdUseCase,
                                           private val albumsRepository : AlbumsRepository,
                                           private val albumStore: AlbumStore
    ) : ViewModel() {
    private val _userState = MutableStateFlow<ResultState<User>>(ResultState.Loading)
    val userState: StateFlow<ResultState<User>> = _userState
    private val _albumsState = MutableStateFlow<ResultState<List<Album>>>(ResultState.Loading)
    val albumsState: StateFlow<ResultState<List<Album>>> = _albumsState
    init {
        fetchRandomUser()
    }

    private fun fetchRandomUser() {
        viewModelScope.launch {
            getUserWithRandomIdUseCase.execute().collect {userResult ->
                _userState.value = userResult
                if (userResult is ResultState.Success){
                    fetchAlbumsForUser(userResult.data.id)
                }
            }
        }
    }
    private fun fetchAlbumsForUser(userId: Int) {
        viewModelScope.launch {
            albumsRepository.getAlbums(userId).collect { albumsResult ->
                _albumsState.value = albumsResult
            }
        }
    }
    fun storeId(album:Album){
        albumStore.album = album
    }

}