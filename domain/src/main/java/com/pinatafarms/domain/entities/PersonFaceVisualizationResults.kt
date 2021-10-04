package com.pinatafarms.domain.entities

import android.graphics.Bitmap

sealed class PersonFaceVisualizationResults(val bitmap: Bitmap) {
    class SuccessfulPersonFaceVisualization(bitmap: Bitmap) : PersonFaceVisualizationResults(bitmap)
    class FaceOutOfBoundsError(bitmap: Bitmap) : PersonFaceVisualizationResults(bitmap)
    class FaceNotDetectedError(bitmap: Bitmap) : PersonFaceVisualizationResults(bitmap)
}