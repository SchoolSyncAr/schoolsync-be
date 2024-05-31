package ar.org.schoolsync.dto.parent

import ar.org.schoolsync.model.Persons.Parent

data class ParentNameOnlyDTO (
    val id: Long,
    val fullName: String
    )

fun Parent.toNameOnlyDTO() = ParentNameOnlyDTO(
    this.id,
    (this.firstName + " " + this.lastName)
)
