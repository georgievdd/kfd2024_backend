package com.example.kbackend.service.impl

import com.example.kbackend.database.dao.UserAuthDao
import com.example.kbackend.database.entity.UserAuthEntity
import com.example.kbackend.dto.auth.AuthRequest
import com.example.kbackend.model.exception.NotFoundException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val dao: UserAuthDao
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = dao.findByEmail(username) ?: throw NotFoundException()
        return user.mapToUserDetails()
    }

    fun createUserDetails(user: AuthRequest): UserDetails =
        User.builder()
            .username(user.email)
            .password(user.password)
            .build()

    private fun UserAuthEntity.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .build()

}