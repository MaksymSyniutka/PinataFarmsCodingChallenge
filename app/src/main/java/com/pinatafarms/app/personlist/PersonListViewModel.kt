package com.pinatafarms.app.personlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinatafarms.domain.entities.Person
import com.pinatafarms.domain.usecases.PersonListReceiveUseCase
import kotlinx.coroutines.flow.*

class PersonListViewModel(
    private val personListReceiveUseCase: PersonListReceiveUseCase
) : ViewModel() {

    val personList: SharedFlow<Result<List<Person>>> = flow {
        emit(personListReceiveUseCase())
    }.shareIn(viewModelScope, SharingStarted.Lazily, 1)
}