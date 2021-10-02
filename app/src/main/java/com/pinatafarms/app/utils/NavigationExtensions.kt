package com.pinatafarms.app.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafely(direction: NavDirections) {
    val action = currentDestination?.getAction(
        direction.actionId
    ) ?: graph.getAction(direction.actionId)
    if (action != null && currentDestination?.id != action.destinationId) {
        navigate(direction)
    }
}