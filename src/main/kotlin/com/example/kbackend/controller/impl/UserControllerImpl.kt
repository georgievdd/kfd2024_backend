package com.example.kbackend.controller.impl

import com.example.kbackend.controller.UserController
import com.example.kbackend.dto.UserResponse
import com.example.kbackend.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserControllerImpl(
    private val userService: UserService
) : UserController {

    @GetMapping("/list")
    override fun getAll(): Iterable<UserResponse> =
        userService.getAll()
}