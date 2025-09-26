package com.example.flixsterplus2app

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlixsterPlus2(
    @SerializedName("name") val name: String?,
    @SerializedName("overview") val description: String?,
    @SerializedName("poster_path") val imagePath: String?
) : Parcelable {
    val imageUrl: String?
        get() = imagePath?.let { "https://image.tmdb.org/t/p/w500$it" }
}
