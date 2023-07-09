package com.venicio.gitfinder.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val avatar_url: String?,
    val followers: String?,
    val following: String?,
    val id: Int?,
    val login: String?,
    val public_repos: String?
) : Parcelable


