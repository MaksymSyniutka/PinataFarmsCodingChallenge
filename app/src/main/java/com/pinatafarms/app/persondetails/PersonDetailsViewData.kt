package com.pinatafarms.app.persondetails

import android.graphics.Bitmap

/**
 * View data class that represents UI state.
 *
 * If any more complicated logic would apply, please switch to using Kotlin Flows or LiveData within this class to deliver
 * changes to UI, so the entire UI will not rebuild, but only a specific part of it which reflects the given change.
 **/
data class PersonDetailsViewData(
    val thumbnail: Bitmap,
    val showErrorMessage: Boolean = false,
    val errorMessage: String = ""
)