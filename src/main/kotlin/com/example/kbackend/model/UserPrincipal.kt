package com.example.kbackend.model

import org.springframework.security.authentication.AbstractAuthenticationToken
import java.util.UUID

class UserPrincipal(
    val email: String,
) : AbstractAuthenticationToken(emptySet()) {

    override fun getCredentials() = null

    override fun getPrincipal() = email
}
