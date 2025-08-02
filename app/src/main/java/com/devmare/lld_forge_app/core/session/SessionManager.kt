package com.devmare.lld_forge_app.core.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut = _isLoggedOut.asStateFlow()

    fun logout() {
        _isLoggedOut.value = true
    }

    fun resetLogoutState() {
        _isLoggedOut.value = false
    }
}
