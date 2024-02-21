package com.example.kbackend.controller

import com.example.kbackend.dto.UserResponse

interface UserController {

    fun getAll(): Iterable<UserResponse>

}