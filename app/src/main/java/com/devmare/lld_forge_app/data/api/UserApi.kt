package com.devmare.lld_forge_app.data.api

import com.devmare.lld_forge_app.data.model.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET("users/me")
    suspend fun fetechUserProfile(): UserResponse
}