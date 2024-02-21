package com.example.kbackend.service.impl

import TokenService
import com.example.kbackend.controller.AuthService
import com.example.kbackend.database.dao.UserAuthDao
import com.example.kbackend.database.dao.UserDao
import com.example.kbackend.database.entity.UserAuthEntity
import com.example.kbackend.database.entity.UserEntity
import com.example.kbackend.dto.auth.AuthRequest
import com.example.kbackend.dto.auth.AuthResponse
import com.example.kbackend.dto.auth.CheckResponse
import com.example.kbackend.dto.auth.TokenPair
import com.example.kbackend.dto.exception.UnauthorizeException
import com.example.kbackend.model.UserPrincipal
import com.example.kbackend.model.exception.AlreadyExistException
import com.example.kbackend.model.exception.NotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val authDao: UserAuthDao,
    private val userDao: UserDao,
    private val tokenService: TokenServiceImpl,
    private val encoder: PasswordEncoder,
) : AuthService {
    override fun signup(data: AuthRequest): TokenPair {
        if (authDao.findByEmail(data.email) != null)
            throw AlreadyExistException()

        val userPrincipal = UserPrincipal(data.email)
        val accessToken = tokenService.generateAccessToken(userPrincipal)
        val refreshToken = tokenService.generateRefreshToken(userPrincipal)

        userDao.save(UserEntity().apply {
            userAuth = UserAuthEntity(
                email = data.email,
                password = encoder.encode(data.password),
                refreshToken = refreshToken
            ).also{
                authDao.save(it)
            }
        })
        return TokenPair(
            refreshToken = refreshToken,
            accessToken = accessToken
        )
    }

    override fun signin(data: AuthRequest): TokenPair {

        val authRefresh = authDao.findByEmail(data.email) ?: throw NotFoundException()

        val userPrincipal = UserPrincipal(data.email)
        val accessToken = tokenService.generateAccessToken(userPrincipal)
        val refreshToken = tokenService.generateRefreshToken(userPrincipal)

        authRefresh.refreshToken = refreshToken
        authDao.save(authRefresh)

        return TokenPair(
            refreshToken = refreshToken,
            accessToken = accessToken,
        )
    }

    override fun refresh(refreshToken: String): AuthResponse {
        authDao.findByRefreshToken(refreshToken) ?: throw NotFoundException()
        val email = tokenService.extractEmail(refreshToken) ?: throw UnauthorizeException()
        val userPrincipal = tokenService.createContext(email)
        val accessToken = tokenService.generateAccessToken(userPrincipal)
        return AuthResponse(
            accessToken = accessToken
        )
    }
}