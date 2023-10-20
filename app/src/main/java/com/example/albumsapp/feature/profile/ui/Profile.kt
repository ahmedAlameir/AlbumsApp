package com.example.albumsapp.feature.profile.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.profile.dataModel.Address
import com.example.albumsapp.feature.profile.dataModel.Album
import com.example.albumsapp.feature.profile.dataModel.User
import com.example.albumsapp.feature.profile.viewModel.ProfileViewModel
@Composable
fun ProfileScreen(
    modifier: Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigate:() -> Unit
) {
    val userState by viewModel.userState.collectAsState()
    val albumsState by viewModel.albumsState.collectAsState()

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)) {
                Text("Profile",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,

                )

            }

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            when (val user = userState) {
                is ResultState.Success -> {
                    DisplayUserProfile(user.data, modifier)
                    Spacer(modifier = Modifier.height(8.dp))
                    DisplayAlbums(albumsState, modifier){album ->
                        viewModel.storeId(album = album)
                        navigate()
                    }
                }
                is ResultState.Error -> {
                    DisplayError(modifier,user.exception.localizedMessage!!)
                }
                ResultState.Loading -> {
                    DisplayLoading(modifier)
                }
            }


        }
    }
}

@Composable
fun DisplayAlbums(
    albumsState: ResultState<List<Album>>,
    modifier: Modifier,
    onItemClick: (Album) -> Unit
) {
    when (albumsState) {
        is ResultState.Success -> {
            val albums = albumsState.data
            Text(
                text = "My Album",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
            DisplayAlbumsList(albums, modifier,onItemClick)
        }
        is ResultState.Error -> {
            DisplayError(modifier,albumsState.exception.localizedMessage!!)
        }
        ResultState.Loading -> {
            DisplayLoading(modifier)
        }
    }
}

@Composable
fun DisplayAlbumsList(albums: List<Album>, modifier: Modifier,onItemClick: (Album) -> Unit) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = albums) { album ->
            AlbumListItem(album,onItemClick)
        }
    }
}

@Composable
fun AlbumListItem(album: Album, onItemClick: (Album) -> Unit) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(album) },
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
        , colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = album.title,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun DisplayUserProfile(user: User, modifier: Modifier) {
    Column {
        Text(
            text = user.name,
            modifier = Modifier.padding(8.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        AddressView(user.address)
    }
}
@Composable
fun AddressView(address: Address, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = "Street: ${address.street}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = "Suite: ${address.suite}")
        Text(text = "City: ${address.city}")
        Text(text = "Zipcode: ${address.zipcode}")

    }
}


@Composable
fun DisplayError(modifier: Modifier,error :String) {
    Text("An error occurred", modifier)
    Text(error, modifier)

    // You can customize the error message as needed
}

@Composable
fun DisplayLoading(modifier: Modifier) {
    Text("Loading...", modifier)
    // You can use a loading indicator here
}