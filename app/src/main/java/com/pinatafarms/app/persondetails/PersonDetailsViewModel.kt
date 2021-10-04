package com.pinatafarms.app.persondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinatafarms.domain.entities.Person
import com.pinatafarms.domain.entities.PersonFaceVisualizationResults
import com.pinatafarms.domain.usecases.PersonFaceVisualizationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class PersonDetailsViewModel(
    private val faceVisualizationUseCase: PersonFaceVisualizationUseCase
) : ViewModel() {

    private val _faceVisualizationResults = MutableSharedFlow<PersonFaceVisualizationResults?>(replay = 1)
    //Using .asSharedFlow() extension so this flow could not be casted to a MutableSharedFlow
    val faceVisualizationResults: SharedFlow<PersonFaceVisualizationResults?> = _faceVisualizationResults.asSharedFlow()

    fun getPersonFaceVisualizationResults(personDetails: Person) {
        viewModelScope.launch {
            _faceVisualizationResults.emit(faceVisualizationUseCase(personDetails))
        }
    }
}