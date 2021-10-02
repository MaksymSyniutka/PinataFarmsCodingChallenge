package com.pinatafarms.data.source.person

import com.pinatafarms.domain.entities.Person

interface PersonDataSource {
    suspend fun getPersonList(): Result<List<Person>>
}