package com.devmare.lld_forge_app.core.dto

data class DefaultResponseDto<T>(
    val status: Status,
    val data: T,
    val message: String,
) {
    enum class Status {
        FAILED,
        SUCCESS
    }
}
