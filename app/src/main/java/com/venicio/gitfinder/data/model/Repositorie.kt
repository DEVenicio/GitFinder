package com.venicio.gitfinder.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Repositorie(
    val name: String
) : Parcelable