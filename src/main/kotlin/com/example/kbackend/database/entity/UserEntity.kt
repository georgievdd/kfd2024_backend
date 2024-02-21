package com.example.kbackend.database.entity

import jakarta.persistence.*

@Entity
class UserEntity : AbstractEntity() {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userAuthId")
    var userAuth: UserAuthEntity? = null
}