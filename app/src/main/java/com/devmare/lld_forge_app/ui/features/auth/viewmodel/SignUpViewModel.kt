package com.devmare.lld_forge_app.ui.features.auth.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmare.lld_forge_app.core.utils.ExceptionUtils.Companion.extractErrorMessage
import com.devmare.lld_forge_app.data.model.SignupRequest
import com.devmare.lld_forge_app.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {
    private val _uiState = mutableStateOf<SignUpUIState>(SignUpUIState.Idle)
    val uiState: State<SignUpUIState> = _uiState

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = SignUpUIState.Loading
            try {
                val request = SignupRequest(name, email, password)
                val response = signUpUseCase(request)
                _uiState.value = SignUpUIState.Success(response.message)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorBody)
                _uiState.value = SignUpUIState.Error(errorMessage)
            } catch (e: Exception) {
                _uiState.value = SignUpUIState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun reset() {
        _uiState.value = SignUpUIState.Idle
    }
}