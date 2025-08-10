package com.devmare.lld_forge_app.ui.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmare.lld_forge_app.core.dto.MentorshipSessionResponseDto
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.domain.usecase.BookMentorshipSessionUsecase
import com.devmare.lld_forge_app.domain.usecase.FetchMentorshipSessionsUsecase
import com.devmare.lld_forge_app.core.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MentorshipViewModel @Inject constructor(
    private val bookSessionUsecase: BookMentorshipSessionUsecase,
    private val fetchMentorshipSessionsUsecase: FetchMentorshipSessionsUsecase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookSessionUiState>(BookSessionUiState.Idle)
    val uiState: StateFlow<BookSessionUiState> = _uiState

    private val _paymentResult = MutableSharedFlow<Result<String>>()
    val paymentResult: SharedFlow<Result<String>> = _paymentResult

    fun emitPaymentSuccess(id: String) {
        viewModelScope.launch {
            _paymentResult.emit(Result.success(id))
        }
    }

    fun emitPaymentError(message: String) {
        viewModelScope.launch {
            _paymentResult.emit(Result.failure(Exception(message)))
        }
    }

    fun bookSession(request: MentorshipBookingRequest) {
        viewModelScope.launch {
            _uiState.value = BookSessionUiState.Loading
            try {
                val response = bookSessionUsecase(request)
                _uiState.value = BookSessionUiState.Success(response.data.data)
            } catch (e: Exception) {
                _uiState.value = BookSessionUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private val _sessionsState =
        MutableStateFlow<UiState<List<MentorshipSessionResponseDto>>>(UiState.Idle)
    val sessionsState: StateFlow<UiState<List<MentorshipSessionResponseDto>>> = _sessionsState

    fun fetchSessions() {
        viewModelScope.launch {
            _sessionsState.value = UiState.Loading
            try {
                val response = fetchMentorshipSessionsUsecase()
                _sessionsState.value = UiState.Success(response.data.data)
            } catch (e: Exception) {
                _sessionsState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _uiState.value = BookSessionUiState.Idle
    }
}