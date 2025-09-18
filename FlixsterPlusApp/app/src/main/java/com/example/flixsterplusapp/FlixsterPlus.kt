package com.example.flixsterplusapp
import com.google.gson.annotations.SerializedName


    data class FlixsterPlus(
        @SerializedName("title") val name: String,
        @SerializedName("overview") val description: String,
        @SerializedName("poster_path") val imagePath: String
    ) {
        val imageUrl: String
            get() = "https://image.tmdb.org/t/p/w500$imagePath"
    }