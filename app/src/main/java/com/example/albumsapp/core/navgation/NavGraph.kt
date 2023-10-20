package com.example.albumsapp.core.navgation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.albumsapp.feature.album.ui.AlbumScreen
import com.example.albumsapp.feature.photo.ui.PhotoScreen
import com.example.albumsapp.feature.profile.ui.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController =navController,
        startDestination =  Screens.Profile.route
    ){
        composable(Screens.Profile.route){
            ProfileScreen(modifier = Modifier){
                navController.navigate(Screens.Album.route)
            }
        }
        composable(Screens.Album.route){
            AlbumScreen(modifier = Modifier , navigate =  {
                navController.navigate(Screens.Photo.route)

            }, navigateBack = {
                navController.popBackStack()

            })
            
        }
        composable(Screens.Photo.route){
          PhotoScreen(modifier = Modifier) {
              navController.popBackStack()
          }  
        }
    }
}