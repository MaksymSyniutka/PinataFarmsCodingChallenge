package com.pinatafarms.data.utils

import android.graphics.Bitmap
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun FaceDetector.processAsync(inputImage: InputImage): Result<List<Face>> {
    return process(inputImage).await()
}

suspend fun <T> Task<T>.await(): Result<T> {
    return suspendCancellableCoroutine { continuation ->
        this.addOnCompleteListener {
            val exception = it.exception
            when {
                it.isSuccessful -> continuation.resume(Result.success(it.result))
                it.isCanceled -> continuation.cancel()
                exception != null -> continuation.resumeWithException(exception)
            }
        }
    }
}

val Face.boundingBoxArea: Int
    get() = boundingBox.width() * boundingBox.height()

fun Face.boundingBoxAreaComparedToBitmap(
    bitmap: Bitmap
): Double = boundingBoxArea.toDouble() / bitmap.area.toDouble() * 100.0