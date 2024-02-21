package com.example.kbackend.service

import com.example.kbackend.dto.UserResponse


interface UserService {

    fun getAll(): Iterable<UserResponse>

}