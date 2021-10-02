package com.pinatafarms.data.utils

import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.Rect

val Bitmap.area: Int
    get() = width * height

val Rect.centerPointF: PointF
    get() = PointF(centerX().toFloat(), centerY().toFloat())