package com.pinatafarms.data.repositories

import com.pinatafarms.data.AppDispatchers
import com.pinatafarms.data.source.person.PersonDataSource
import com.pinatafarms.data.source.person.PersonFaceDetectionDataSource
import com.pinatafarms.domain.entities.Person
import com.pinatafarms.domain.repositories.PersonRepository
import kotlinx.coroutines.withContext

/**
 * Repository which fetches person's data and runs face detection on fetched person's list.
 *
 * @property personDataSource fetches person's data from local source.
 * TODO:
 *  In order to fetch this data from remote - change this property to any remote data source (not present in the app at the moment).
 *  No other changes required.
 **/
class PersonRepositoryImpl(
    private val personDataSource: PersonDataSource,
    private val personFaceDetectionDataSource: PersonFaceDetectionDataSource,
    private val appDispatchers: AppDispatchers
) : PersonRepository {
    override suspend fun getPersonList(): Result<List<Person>> = withContext(appDispatchers.IO) {
        personDataSource.getPersonList().fold(
            onSuccess = {
                Result.success(personFaceDetectionDataSource.runFaceDetection(it))
            }, onFailure = {
                Result.failure(it)
            }
        )
    }
}