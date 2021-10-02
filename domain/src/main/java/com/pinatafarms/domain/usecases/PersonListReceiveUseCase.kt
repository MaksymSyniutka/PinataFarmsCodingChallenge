package com.pinatafarms.domain.usecases

import com.pinatafarms.domain.entities.Person
import com.pinatafarms.domain.repositories.PersonRepository

interface PersonListReceiveUseCase {
    suspend operator fun invoke(): Result<List<Person>>
}

class PersonListReceiveUseCaseImpl(
    private val personRepository: PersonRepository
) : PersonListReceiveUseCase {
    override suspend fun invoke(): Result<List<Person>> {
        return personRepository.getPersonList().fold(
            onSuccess = { personList ->
                Result.success(
                    personList.sortedWith(compareByDescending<Person> { it.faceArea }.thenBy { it.fullName })
                )
            }, onFailure = { throwable ->
                Result.failure(throwable)
            }
        )
    }
}