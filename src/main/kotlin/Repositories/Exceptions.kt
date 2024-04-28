package ar.org.schoolsync.Repositories

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class IdInvalido(message: String) : RuntimeException(message)