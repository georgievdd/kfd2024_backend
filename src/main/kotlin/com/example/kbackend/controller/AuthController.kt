package com.example.kbackend.controller

import com.example.kbackend.dto.auth.AuthRequest
import com.example.kbackend.dto.auth.AuthResponse
import com.example.kbackend.dto.auth.CheckResponse
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

interface AuthController {

    /**
     * signup и signin принимают креды, создают новую пару, refresh кладут в бд,
     * возвращают accessToken и кладут в cookie refreshToken
     */

    fun signup(data: AuthRequest): ResponseEntity<AuthResponse>
    fun signin(data: AuthRequest): ResponseEntity<AuthResponse>

    /**
     * refresh создает новую пару, refresh перезаписывает в бд
     * возвращает новый accessToken
     */
    fun refresh(refreshToken: String): AuthResponse


}