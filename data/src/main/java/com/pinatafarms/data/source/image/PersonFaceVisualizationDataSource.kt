package com.pinatafarms.data.source.image

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.contains
import com.pinatafarms.data.AppDispatchers
import com.pinatafarms.data.utils.centerPointF
import com.pinatafarms.domain.entities.Person
import com.pinatafarms.domain.entities.PersonFaceVisualizationResults
import kotlinx.coroutines.withContext

interface PersonFaceVisualizationDataSource {
    suspend fun getFaceDetectionResults(personDetails: Person): PersonFaceVisualizationResults?
}

class PersonFaceVisualizationDataSourceImpl(
    private val appDispatchers: AppDispatchers,
    private val personBitmapLruCacheDataSource: PersonBitmapLruCacheDataSource
) : PersonFaceVisualizationDataSource {
    override suspend fun getFaceDetectionResults(
        personDetails: Person
    ): PersonFaceVisualizationResults? = withContext(appDispatchers.Default) {
        personBitmapLruCacheDataSource.saveOrGetBitmapFromCache(personDetails.imagePath)?.let { bitmap ->
            val faceBounds = personDetails.faceBounds
            if (personDetails.hasFaceBeenDetected && faceBounds != null) {
                if (!bitmap.contains(faceBounds.centerPointF)) {
                    PersonFaceVisualizationResults.FaceOutOfBoundsError(bitmap)
                } else {
                    val paint = Paint().apply {
                        strokeWidth = 5f
                        color = Color.RED
                        style = Paint.Style.STROKE
                    }
                    val mutableBitmap = bitmap.copy(
                        Bitmap.Config.RGB_565, true
                    ).applyCanvas { drawRect(faceBounds, paint) }
                    PersonFaceVisualizationResults.SuccessfulPersonFaceVisualization(mutableBitmap)
                }
            } else {
                PersonFaceVisualizationResults.FaceNotDetectedError(bitmap)
            }
        }
    }
}