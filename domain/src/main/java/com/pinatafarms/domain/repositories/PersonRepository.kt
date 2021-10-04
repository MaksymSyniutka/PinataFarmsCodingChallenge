package com.pinatafarms.domain.repositories

import com.pinatafarms.domain.entities.Person

interface PersonRepository {
    suspend fun getPersonList(): Result<List<Person>>
}