package com.example.kbackend.dto.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

interface ApiResponse {
    val status: HttpStatus
    val message: String

    fun asResponse() =
        ResponseEntity
            .status(status)
            .body(this)
}