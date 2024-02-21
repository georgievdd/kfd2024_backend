package com.example.kbackend.model.exception

import com.example.kbackend.dto.exception.ApiResponse
import com.fasterxml.jackson.annotation.JsonIncludeProperties
import org.springframework.http.HttpStatus

@JsonIncludeProperties("status", "message")
abstract class AbstractApiException : ApiResponse, Exception() {
    override val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    override val message: String
        get() = localizedMessage

}