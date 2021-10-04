package com.pinatafarms.domain.repositories

import com.pinatafarms.domain.entities.Person
import com.pinatafarms.domain.entities.PersonFaceVisualizationResults

interface PersonRepository {
    suspend fun getPersonList(): Result<List<Person>>

    suspend fun getPersonFaceVisualization(personDetails: Person): PersonFaceVisualizationResults?
}