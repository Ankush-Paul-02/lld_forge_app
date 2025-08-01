package com.devmare.lld_forge_app.data.repository

import com.devmare.lld_forge_app.data.api.UserApi
import com.devmare.lld_forge_app.data.model.UserResponse
import com.devmare.lld_forge_app.domain.repository.UserRepository

class UserRepositoryImpl(private val userApi: UserApi) : UserRepository {

    override suspend fun fetechUserProfile(): UserResponse {
        return userApi.fetechUserProfile()
    }
}