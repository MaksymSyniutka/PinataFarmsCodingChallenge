package com.pinatafarms.data.entity

import com.pinatafarms.domain.entities.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
internal data class PersonResponse(
    @SerialName("name")
    val fullName: String,
    @SerialName("img")
    private val imageName: String,
    @Transient
    val faceArea: Double = Person.INVALID_FACE_AREA
) {
    val imagePath: String = "android_guys/$imageName"
}

internal fun PersonResponse.transformToPerson(): Person = Person(
    fullName, imagePath, faceArea
)