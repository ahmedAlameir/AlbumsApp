package com.example.albumsapp.core.navgation

sealed class Screens(val route: String) {
    object Profile : Screens("profile")
    object Album : Screens("album")
    object Photo : Screens("Photo")

}