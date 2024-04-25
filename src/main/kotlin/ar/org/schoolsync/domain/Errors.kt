package ar.org.schoolsync.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

object UserError {
    const val INVALID_CREDENTIALS = "Las credenciales suministradas son incorrectas"
}

object RepositoryError {
    const val ELEMENT_NOT_FOUND = "Elemento no encontrado"
}

object Error {
    const val GENERAL_ERROR = "Error desconocido"
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class IllegalArgumentException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
class NotImplementedError(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthenticationException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerError(msg: String) : RuntimeException(msg)