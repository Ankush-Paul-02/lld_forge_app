package com.devmare.lld_forge_app.core.interceptor

interface TokenProvider {
    suspend fun getToken(): String?
}