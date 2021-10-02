package com.pinatafarms.data.source.person

import android.content.Context
import com.pinatafarms.data.utils.KotlinSerialization
import com.pinatafarms.data.entity.PersonResponse
import com.pinatafarms.data.entity.transformToPerson
import com.pinatafarms.domain.entities.Person
import kotlinx.serialization.decodeFromString
import timber.log.Timber
import java.io.IOException

class PersonLocalDataSource(
    private val applicationContext: Context,
    private val kotlinSerialization: KotlinSerialization
) : PersonDataSource {
    override suspend fun getPersonList(): Result<List<Person>> = kotlin.runCatching {
        Timber.d("reading person details list from local .json file")
        val personJson = try {
            applicationContext.assets.open("android_guys/android_guys.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Timber.w(ioException, ".json file parsing has failed")
            return Result.failure(ioException)
        }
        return Result.success(
            kotlinSerialization.decodeFromString<List<PersonResponse>>(personJson).map {
                it.transformToPerson()
            }
        )
    }
}