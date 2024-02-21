package com.example.kbackend.database.dao

import com.example.kbackend.database.entity.UserAuthEntity
import com.example.kbackend.database.dao.CommonDao
import org.springframework.stereotype.Repository

interface UserAuthDao : CommonDao<UserAuthEntity> {
    fun findByEmail(email: String): UserAuthEntity?

    fun findByRefreshToken(refreshToken: String): UserAuthEntity?
}