package com.devmare.lld_forge_app.core.dto

data class DefaultResponseDto(
    val status: Status,
    val data: Map<String, Any> = emptyMap(),
    val message: String,
) {
    enum class Status {
        FAILED,
        SUCCESS
    }
}