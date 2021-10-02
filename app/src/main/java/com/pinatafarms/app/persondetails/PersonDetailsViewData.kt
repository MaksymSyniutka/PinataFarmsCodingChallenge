package com.pinatafarms.app.persondetails

import android.graphics.Bitmap

data class PersonDetailsViewData(
    val thumbnail: Bitmap,
    val showErrorMessage: Boolean = false,
    val errorMessage: String = ""
)