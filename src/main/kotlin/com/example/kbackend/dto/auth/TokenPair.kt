package com.example.kbackend.dto.auth

data class TokenPair(
    val refreshToken: String,
    val accessToken: String,
)
