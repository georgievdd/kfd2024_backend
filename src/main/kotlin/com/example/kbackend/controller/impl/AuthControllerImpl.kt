package com.example.kbackend.controller.impl

import com.example.kbackend.controller.AuthController
import com.example.kbackend.dto.auth.AuthRequest
import com.example.kbackend.dto.auth.AuthResponse
import com.example.kbackend.dto.auth.CheckResponse
import com.example.kbackend.dto.auth.TokenPair
import com.example.kbackend.service.impl.AuthServiceImpl
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthControllerImpl(
    private val authService: AuthServiceImpl
) : AuthController {

    @PostMapping("/signup")
    override fun signup(@RequestBody data: AuthRequest): ResponseEntity<AuthResponse> =
        authResponseWithCookie(authService.signup(data))

    @PostMapping("/signin")
    override fun signin(@RequestBody data: AuthRequest): ResponseEntity<AuthResponse> =
        authResponseWithCookie(authService.signin(data))

    @PostMapping("/refresh")
    override fun refresh(@CookieValue refreshToken: String): AuthResponse {
        return authService.refresh(refreshToken)
    }

    private fun authResponseWithCookie(tokens: TokenPair): ResponseEntity<AuthResponse> {
        val cookieValue = "refreshToken=${tokens.refreshToken}; Path=/; HttpOnly"
        val headers = HttpHeaders().apply {
            add("Set-Cookie", "$cookieValue; Secure")
        }
        val body = AuthResponse(accessToken = tokens.accessToken)
        return ResponseEntity.ok().headers(headers).body(body)
    }
}