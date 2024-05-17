package ar.org.schoolsync.exeptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus



object CreationError {
    const val CANNOT_CREATE_USER = "El usuario no pudo ser creado correctamente"
}

object FindError {
    @Suppress("FunctionName")
    fun USER_NOT_FOUND(id:Long) = "No existe el usuario con id: $id "

    @Suppress("FunctionName")
    fun USER_NOT_FOUND(username:String) = "No existe el usuario con username: $username "
}


object NotificationCreationError {
    const val CANNOT_CREATE_NOTIFICATION = "La notificación no pudo ser creada correctamente"
}


@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResponseFindException(message: String) : RuntimeException(message)

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class ResponseStatusException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class Businessexception(msg:String): RuntimeException(msg)