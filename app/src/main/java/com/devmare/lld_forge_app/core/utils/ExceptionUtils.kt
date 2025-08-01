package com.devmare.lld_forge_app.core.utils

import org.json.JSONObject

class ExceptionUtils {
    companion object {
        fun extractErrorMessage(errorBody: String?): String {
            return try {
                val jsonObject = JSONObject(errorBody ?: "")
                jsonObject.optString("message", "Something went wrong")
            } catch (e: Exception) {
                "Something went wrong"
            }
        }
    }
}
