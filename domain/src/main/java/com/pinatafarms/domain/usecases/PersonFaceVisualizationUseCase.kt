package com.pinatafarms.domain.usecases

import com.pinatafarms.domain.entities.Person
import com.pinatafarms.domain.entities.PersonFaceVisualizationResults
import com.pinatafarms.domain.repositories.PersonRepository

interface PersonFaceVisualizationUseCase {
    suspend operator fun invoke(personDetails: Person): PersonFaceVisualizationResults?

}

class PersonFaceVisualizationUseCaseImpl(
    private val personRepository: PersonRepository
) : PersonFaceVisualizationUseCase {
    override suspend fun invoke(personDetails: Person): PersonFaceVisualizationResults? {
        return personRepository.getPersonFaceVisualization(personDetails)
    }
}