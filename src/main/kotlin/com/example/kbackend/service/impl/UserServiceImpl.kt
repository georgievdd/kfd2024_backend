package com.example.kbackend.service.impl

import com.example.kbackend.database.dao.UserAuthDao
import com.example.kbackend.database.dao.UserDao
import com.example.kbackend.database.entity.UserEntity
import com.example.kbackend.dto.UserResponse
import com.example.kbackend.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userDao: UserDao,
    private val userAuthDao: UserAuthDao,
) : UserService {
    override fun getAll(): Iterable<UserResponse> {
        return userDao.findAll().map { user -> user.mapToResponse() }
    }

    private fun UserEntity.mapToResponse() =
        UserResponse(
            email = userAuth!!.email,
            id = id,
            createdAt = createdAt,
        )

}