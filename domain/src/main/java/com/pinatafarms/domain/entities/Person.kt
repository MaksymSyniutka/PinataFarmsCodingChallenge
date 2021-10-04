package com.pinatafarms.domain.entities

import android.graphics.Rect
import android.os.Parcelable
import com.pinatafarms.domain.AppConstants
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val fullName: String,
    val imagePath: String,
    val faceArea: Double = INVALID_FACE_AREA,
    val faceBounds: Rect? = null
) : Parcelable {
    @IgnoredOnParcel
    val hasFaceBeenDetected = faceArea != INVALID_FACE_AREA

    @IgnoredOnParcel
    val relativeImageFilePath = AppConstants.ASSETS_FILE_PATH_PREFIX + imagePath

    companion object {
        const val INVALID_FACE_AREA: Double = -1.0
    }
}