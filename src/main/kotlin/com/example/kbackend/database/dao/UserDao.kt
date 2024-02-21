package com.example.kbackend.database.dao

import com.example.kbackend.database.entity.UserEntity
import org.springframework.data.jdbc.repository.query.Query

interface UserDao : CommonDao<UserEntity>