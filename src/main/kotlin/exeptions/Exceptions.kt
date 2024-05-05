package ar.org.schoolsync.exeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID


object CreationError {
    const val CANNOT_CREATE_USER = "El usuario no pudo ser creado correctamente"
}

object FindError {
    @Suppress("FunctionName")
    fun USER_NOT_FOUND(uuid:UUID) = "No existe el usuario con id: $uuid "

    @Suppress("FunctionName")
    fun USER_NOT_FOUND(username:String) = "No existe el usuario con username: $username "
}


@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResponseFindException(message: String) : RuntimeException(message)

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ResponseStatusException(message: String) : RuntimeException(message)