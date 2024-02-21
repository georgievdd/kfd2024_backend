package com.example.kbackend.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne
import org.springframework.security.core.userdetails.User

@Entity
class UserAuthEntity(
    @Column(length = 30, nullable = false)
    var email: String = "",
    @Column(length = 255, nullable = false)
    var password: String = "",
    @Column(length = 255, nullable = false)
    var refreshToken: String = "",
) : AbstractEntity()