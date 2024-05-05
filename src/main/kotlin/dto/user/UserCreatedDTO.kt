package ar.org.schoolsync.dto.user

import ar.org.schoolsync.model.User
import java.util.*

data class UserCreatedDTO (
    val id: UUID,
    val email: String,
)

fun User.toCreatedDTO() = UserCreatedDTO(
    id = this.id,
    email = this.email
)