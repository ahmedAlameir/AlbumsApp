package com.example.albumsapp.feature.album.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.albumsapp.R
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.dataModel.Photo
import com.example.albumsapp.feature.album.viewModel.AlbumViewModel
import com.example.albumsapp.feature.profile.ui.DisplayAlbumsList
import com.example.albumsapp.feature.profile.ui.DisplayError
import com.example.albumsapp.feature.profile.ui.DisplayLoading

@Composable
fun AlbumScreen(
    modifier: Modifier,
    viewModel: AlbumViewModel = hiltViewModel(),
    navigate:() -> Unit,
    navigateBack:() -> Unit

) {
    var searchText by remember { mutableStateOf("") }

    val photoState by viewModel.photoState.collectAsState()
    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier= Modifier.clickable {
                        navigateBack()
                    }.size(36.dp),
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "back arrow"
                )
                Spacer(modifier = Modifier.width(16.dp))

                Text("Album",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            }

        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TextField(
                value = searchText,
                onValueChange = { text->
                    searchText = text
                    viewModel.fetchPhoto(text)
                },
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

            when (val photos = photoState) {
                is ResultState.Success -> {

                    PhotoList(photos.data){photo->
                        viewModel.storeImage(photo)
                        navigate()
                    }
                }

                is ResultState.Error -> {
                    DisplayError(modifier,photos.exception.localizedMessage!!)
                }

                ResultState.Loading -> {
                    DisplayLoading(modifier)
                }
            }

        }
    }
}
@Composable
fun PhotoList(photos:List<Photo>,onItemSelected: (Photo) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(photos) { photo ->
            PhotoItem(photo,onItemSelected)
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onItemSelected: (Photo) -> Unit) {
    Card(
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemSelected(photo) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AsyncImage(model = photo.url, contentDescription =photo.title )
        
    }
}