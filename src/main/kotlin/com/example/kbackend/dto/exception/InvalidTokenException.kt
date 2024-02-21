package com.example.kbackend.model.exception

import org.springframework.http.HttpStatus

class InvalidTokenException : AbstractApiException() {
    override val message: String
        get() = "invalid token"
    override val status = HttpStatus.UNAUTHORIZED
}