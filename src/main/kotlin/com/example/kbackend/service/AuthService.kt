package com.example.kbackend.controller

import com.example.kbackend.dto.auth.AuthRequest
import com.example.kbackend.dto.auth.AuthResponse
import com.example.kbackend.dto.auth.TokenPair

interface AuthService {

    fun signup(data: AuthRequest): TokenPair
    fun signin(data: AuthRequest): TokenPair
    fun refresh(refreshToken: String): AuthResponse

}