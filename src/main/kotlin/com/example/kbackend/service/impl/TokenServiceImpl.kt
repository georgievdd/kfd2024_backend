package com.example.kbackend.service.impl

import TokenService
import com.example.kbackend.config.JwtProperties
import com.example.kbackend.model.UserPrincipal
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenServiceImpl(
    private val jwtProperties: JwtProperties
) : TokenService {
    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    private fun generate(
        userPrincipal: UserPrincipal,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String =
        Jwts.builder()
            .claims()
            .subject(userPrincipal.email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expirationDate)
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()


    private fun getAllClaims(token: String): Claims =
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload

    override fun generateRefreshToken(userPrincipal: UserPrincipal) =
        generate(
            userPrincipal = userPrincipal,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
        )

    override fun generateAccessToken(userPrincipal: UserPrincipal) =
        generate(
            userPrincipal = userPrincipal,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )

    override fun extractEmail(token: String): String? =
        getAllClaims(token)
            .subject

    override fun isExpired(token: String): Boolean =
        getAllClaims(token)
            .expiration
            .before(Date(System.currentTimeMillis()))

    override fun createContext(token: String): UserPrincipal {
        return UserPrincipal(token)
    }

}