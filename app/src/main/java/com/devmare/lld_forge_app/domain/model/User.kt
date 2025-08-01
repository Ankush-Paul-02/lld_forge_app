package com.devmare.lld_forge_app.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val joinedAt: Long,
    val isEmailVerified: Boolean,
    val enabled: Boolean,
    val accountNonLocked: Boolean,
    val credentialsNonExpired: Boolean,
    val accountNonExpired: Boolean,
    val username: String,
    val authorities: List<Authority>,
)

data class Authority(
    val authority: String,
)