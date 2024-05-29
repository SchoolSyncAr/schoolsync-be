package ar.org.schoolsync.dto.user

import ar.org.schoolsync.model.Persons.Parent

data class ParentDTO (
    val id: Long,
    val fullName: String
)

fun Parent.toDTO() = ParentDTO(
    this.id,
    (this.firstName + " " + this.lastName)
)