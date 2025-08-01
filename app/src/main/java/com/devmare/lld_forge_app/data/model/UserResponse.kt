package com.devmare.lld_forge_app.data.model

import com.devmare.lld_forge_app.domain.model.User

data class UserResponse(
    val status: String,
    val data: UserData,
    val message: String,
)

data class UserData(
    val user: User,
)