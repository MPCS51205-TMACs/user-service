package com.mpcs51205.userservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class BlockedUserException(private val email: String) : RuntimeException() {
    override val message: String
        get() = "$email has been blocked"
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceDoesNotExistException : RuntimeException()

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnauthorizedException : RuntimeException()

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AccountSuspendedException : RuntimeException()

@ResponseStatus(HttpStatus.BAD_REQUEST)
class EmailAlreadyExists: RuntimeException()