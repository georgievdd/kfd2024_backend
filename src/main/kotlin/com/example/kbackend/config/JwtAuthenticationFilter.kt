package com.example.kbackend.config

import com.example.kbackend.controller.ExceptionResolver
import com.example.kbackend.model.exception.ApiException
import com.example.kbackend.service.impl.TokenServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter(
    private val tokenService: TokenServiceImpl,
    private val exceptionResolver: ExceptionResolver
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authHeader: String? = request.getHeader("Authorization")
            if (authHeader.doesNotContainBearerToken()) {
                filterChain.doFilter(request, response)
                return
            }
            val jwtToken = authHeader!!.extractToken()
            if (tokenService.isExpired(jwtToken)) {
                throw ApiException(HttpStatus.FORBIDDEN, message = "token expired")
            }
            val email = tokenService.extractEmail(jwtToken)!!
            SecurityContextHolder.getContext().authentication = tokenService.createContext(email)
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            exceptionResolver.resolveException(request, response, ApiException(HttpStatus.FORBIDDEN, message = e.message.orEmpty()))
        }
    }

    private fun String?.doesNotContainBearerToken(): Boolean =
        this == null || !this.startsWith("Bearer ")

    private fun String.extractToken(): String =
        this.substringAfter("Bearer ")
}