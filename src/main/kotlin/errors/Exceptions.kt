package ar.org.schoolsync.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID


object CreationError {
    const val CANNOT_CREATE_USER = "El usuario no pudo ser creado correctamente"
}

object FindError {
    @Suppress("FunctionName")
    fun USER_NOT_FOUND(uuid:UUID) = "No existe el usuario con id: $uuid "
}


@ResponseStatus(value = HttpStatus.NOT_FOUND)
class IdInvalido(message: String) : RuntimeException(message)