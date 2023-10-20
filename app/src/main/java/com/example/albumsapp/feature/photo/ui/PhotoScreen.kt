package com.example.albumsapp.feature.photo.ui

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.albumsapp.R
import com.example.albumsapp.core.result.ResultState
import com.example.albumsapp.feature.album.dataModel.Photo
import com.example.albumsapp.feature.photo.viewModel.PhotoViewModel
import com.example.albumsapp.feature.profile.ui.DisplayError
import com.example.albumsapp.feature.profile.ui.DisplayLoading

@Composable
fun PhotoScreen(modifier: Modifier,
                viewModel: PhotoViewModel = hiltViewModel(),
                navigateBack:() -> Unit){

    val photoState by viewModel.photoState.collectAsState()
     when(val photo = photoState){
        is ResultState.Success->{
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, photo.data.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            val context = LocalContext.current

            Scaffold(
                topBar = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        Image(
                            modifier= Modifier.clickable {
                                navigateBack()
                            }.size(36.dp),
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "back arrow"
                        )

                        Text(modifier = Modifier.width(256.dp),
                            text = photo.data.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            )

                        Image(
                            modifier= Modifier.clickable {
                                context.startActivity(shareIntent)
                            }.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "share"
                        )
                    }

                }
            ){
                ZoomableImage(modifier.padding(it),photo.data)

            }
        }
         is ResultState.Error -> {
             DisplayError(modifier,photo.exception.localizedMessage!!)
         }
         ResultState.Loading -> {
             DisplayLoading(modifier)
         }
    }


}
@Composable
fun ZoomableImage(modifier: Modifier,photo: Photo) {
    val scale = remember { mutableFloatStateOf(1f) }
    val rotationState = remember { mutableFloatStateOf(1f) }
    Box(
        modifier = modifier
            .clip(RectangleShape)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, rotation ->
                    scale.floatValue *= zoom
                    rotationState.floatValue += rotation
                }
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = maxOf(.5f, minOf(5f, scale.floatValue)),
                    scaleY = maxOf(.5f, minOf(5f, scale.floatValue)),
                    rotationZ = rotationState.floatValue
                ),
            contentDescription = null,
            model = photo.url
        )
    }
}