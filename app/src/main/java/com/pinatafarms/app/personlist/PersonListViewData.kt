package com.pinatafarms.app.personlist

/**
 * View data class that represents UI state.
 *
 * If any more complicated logic would apply, please switch to using Kotlin Flows or LiveData within this class to deliver
 * changes to UI, so the entire UI will not rebuild, but only a specific part of it which reflects the given change.
 **/
data class PersonListViewData(
    val isLoading: Boolean = false
)