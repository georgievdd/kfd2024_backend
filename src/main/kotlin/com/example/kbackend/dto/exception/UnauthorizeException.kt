package com.example.kbackend.dto.exception

import com.example.kbackend.model.exception.AbstractApiException
import org.springframework.http.HttpStatus

class UnauthorizeException  : AbstractApiException() {
    override val message: String
        get() = "unauthorized"
    override val status = HttpStatus.UNAUTHORIZED
}