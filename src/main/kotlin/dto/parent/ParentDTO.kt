package ar.org.schoolsync.dto.parent

import ar.org.schoolsync.model.users.Parent

data class ParentDTO (
    val id: Long,
    val firstName: String,
    val lastName: String,
    val notificationGroups: List<String>
    )

fun Parent.toDTO() = ParentDTO(
    this.id,
    this.firstName,
    this.lastName,
    this.notificationGroups.map { it.name }
)
