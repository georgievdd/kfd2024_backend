package com.example.kbackend.model.exception

import org.springframework.http.HttpStatus

class BadRequestException : AbstractApiException() {
    override val message: String
        get() = "Bad Request"
    override val status = HttpStatus.BAD_REQUEST
}