package com.example.kbackend.model.exception

import org.springframework.http.HttpStatus

class AlreadyExistException : AbstractApiException() {
    override val message: String
        get() = "Already exist"
    override val status = HttpStatus.BAD_REQUEST
}