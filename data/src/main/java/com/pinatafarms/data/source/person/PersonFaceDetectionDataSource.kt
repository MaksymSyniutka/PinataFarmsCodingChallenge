package com.pinatafarms.data.source.person

import androidx.core.graphics.contains
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.pinatafarms.data.*
import com.pinatafarms.data.source.image.PersonBitmapLruCacheDataSource
import com.pinatafarms.data.utils.await
import com.pinatafarms.data.utils.boundingBoxAreaComparedToBitmap
import com.pinatafarms.data.utils.centerPointF
import com.pinatafarms.domain.entities.Person
import kotlinx.coroutines.withContext
import timber.log.Timber

interface PersonFaceDetectionDataSource {
    suspend fun runFaceDetection(personList: List<Person>): List<Person>
}

@Suppress("SimplifiableCallChain")
class PersonFaceDetectionDataSourceImpl(
    private val appDispatchers: AppDispatchers,
    private val personBitmapLruCacheDataSource: PersonBitmapLruCacheDataSource
) : PersonFaceDetectionDataSource {

    /**
     * Runs MLKit Face Detection on a given set of persons.
     * Face Detection options:
     *  - Classification mode disabled, as per acceptance criteria, the categorization was not needed.
     *  - Performance mode set to accurate, as given set of images gives very inconsistent results when running in
     *  fast mode. In addition, performance mode accurate works almost the same in terms of time, and, in the future,
     *  once the API is ready and images will be fetched from the server - we can integrate paging logic which will
     *  help us run face detection on small chunks of data per time, so the performance hit will be barely noticeable.
     *  - Landmark detection disabled as not required by acceptance criteria and it affects performance.
     *  - Contour mode disabled as not required by acceptance criteria and it affects performance.
     *  - Tracking mode disabled as not required by acceptance criteria and it affects performance.
     *
     *  TODO:
     *   The images themselves comes from the data's assets folder, but we can easily change [PersonBitmapLruCacheDataSource]
     *   data source to the one that fetches images from the server and stores them in LRU cache for performance optimization.
     *
     *  Once the Face Detection run has been finished, and in case more than one face has been found - I sort the list
     *  of detected entities and look for the entity with the biggest area covered by the face. The face area is calculated
     *  against its image size and is represented in the percentage of face covered against the whole image.
     *  In addition to entities sorting, I also run a filter on those entities as MLKit often returns detected face's
     *  bounds out of image boundaries.
     **/
    override suspend fun runFaceDetection(
        personList: List<Person>
    ): List<Person> = withContext(appDispatchers.Default) {
        Timber.d("running face detection for: $personList")
        val faceDetectorOptions = FaceDetectorOptions.Builder()
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
            .build()
        val faceDetector = FaceDetection.getClient(faceDetectorOptions)
        return@withContext personList.map { person ->
            val bitmap = personBitmapLruCacheDataSource.saveOrGetBitmapFromCache(
                person.imagePath
            ) ?: kotlin.run {
                Timber.w("was not able to find bitmap for person: $person")
                return@map person.copy(faceArea = Person.INVALID_FACE_AREA, faceBounds = null)
            }

            val inputImage = InputImage.fromBitmap(bitmap, 0)
            return@map faceDetector.process(inputImage).await().fold(
                onSuccess = { facesList ->
                    when {
                        facesList.isEmpty() -> {
                            Timber.d("face detection didn't find any faces for person: $person")
                            return@fold person.copy(
                                faceArea = Person.INVALID_FACE_AREA,
                                faceBounds = null
                            )
                        }
                        facesList.size == 1 -> {
                            Timber.d("face detection found one face for person: $person")
                            val firstEntry = facesList.first()
                            return@fold person.copy(
                                faceArea = firstEntry.boundingBoxAreaComparedToBitmap(bitmap),
                                faceBounds = firstEntry.boundingBox
                            )
                        }
                        else -> {
                            Timber.d("face detection has found more than one face for person: $person")
                            val firstEntry = facesList
                                .filter { bitmap.contains(it.boundingBox.centerPointF) }
                                .sortedByDescending { it.boundingBoxAreaComparedToBitmap(bitmap) }
                                .first()
                            return@fold person.copy(
                                faceArea = firstEntry.boundingBoxAreaComparedToBitmap(bitmap),
                                faceBounds = firstEntry.boundingBox
                            )
                        }
                    }
                }, onFailure = {
                    Timber.w(it, "face detection has failed for person: $person")
                    return@fold person.copy(
                        faceArea = Person.INVALID_FACE_AREA,
                        faceBounds = null
                    )
                }
            )
        }
    }
}