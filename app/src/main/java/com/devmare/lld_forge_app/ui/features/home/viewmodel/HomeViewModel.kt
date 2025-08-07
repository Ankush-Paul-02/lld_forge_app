package com.devmare.lld_forge_app.ui.features.home.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmare.lld_forge_app.core.exception.AppInfoException
import com.devmare.lld_forge_app.core.utils.ExceptionUtils.Companion.extractErrorMessage
import com.devmare.lld_forge_app.domain.usecase.EnsureValidTokenUseCase
import com.devmare.lld_forge_app.domain.usecase.FetchLeaderboardMentorsUseCase
import com.devmare.lld_forge_app.domain.usecase.UserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUsecase: UserUsecase,
    private val fetchLeaderboardMentorsUseCase: FetchLeaderboardMentorsUseCase,
    private val ensureValidTokenUseCase: EnsureValidTokenUseCase,
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeUIState>(HomeUIState.Idle)
    val homeState: MutableStateFlow<HomeUIState> = _homeState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun fetchCurrentUserProfile() {
        viewModelScope.launch {
            _homeState.value = HomeUIState.Loading

            val tokenStillValid = ensureValidTokenUseCase.execute()
            if (!tokenStillValid) {
                _homeState.value = HomeUIState.SessionExpired
                return@launch
            }

            try {
                val user = userUsecase()
                val leaderboardMentors = fetchLeaderboardMentorsUseCase()
                _homeState.value = HomeUIState.Success(user.data.user, leaderboardMentors.data.data)
            } catch (e: HttpException) {
                throw AppInfoException(extractErrorMessage(e.response()?.errorBody()?.string()))
            } catch (e: AppInfoException) {
                _homeState.value = HomeUIState.Error(e.message ?: "AppInfo error")
            } catch (e: Exception) {
                _homeState.value = HomeUIState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun resetState() {
        _homeState.value = HomeUIState.Idle
    }
}