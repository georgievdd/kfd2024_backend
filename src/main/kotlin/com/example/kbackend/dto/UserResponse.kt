package com.example.kbackend.dto

import java.time.LocalDateTime
import java.util.UUID

data class UserResponse(
    val id: UUID,
    val email: String,
    val createdAt: LocalDateTime,
)
