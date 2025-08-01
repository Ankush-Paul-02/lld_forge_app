package com.devmare.lld_forge_app.ui.features.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmare.lld_forge_app.core.prefs.DataStoreManager
import com.devmare.lld_forge_app.core.utils.ExceptionUtils.Companion.extractErrorMessage
import com.devmare.lld_forge_app.data.model.LoginRequest
import com.devmare.lld_forge_app.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    var state by mutableStateOf(LoginUIState())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                val request = LoginRequest(email, password)
                val response = loginUseCase(request)

                dataStoreManager.saveAccessToken(
                    response.data.data.accessToken,
                    response.data.data.refreshToken
                )

                state = state.copy(
                    isLoading = false,
                    accessToken = response.data.data.accessToken,
                )
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorBody)
                state = state.copy(isLoading = false, error = errorMessage)
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }
}