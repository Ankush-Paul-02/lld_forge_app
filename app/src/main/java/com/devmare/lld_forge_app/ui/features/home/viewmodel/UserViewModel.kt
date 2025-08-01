package com.devmare.lld_forge_app.ui.features.home.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmare.lld_forge_app.core.exception.AppInfoException
import com.devmare.lld_forge_app.domain.usecase.UserUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUsecase: UserUsecase,
) : ViewModel() {

    private val _userState = MutableStateFlow<UserUIState>(UserUIState.Idle)
    val userState: MutableStateFlow<UserUIState> = _userState

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun fetchCurrentUserProfile() {
        viewModelScope.launch {
            _userState.value = UserUIState.Loading
            try {
                val user = userUsecase()
                _userState.value = UserUIState.Success(user.data.user)
            } catch (e: HttpException) {
                throw AppInfoException(extractErrorMessage(e.response()?.errorBody()?.string()))
            } catch (e: AppInfoException) {
                _userState.value = UserUIState.Error(e.message ?: "AppInfo error")
            } catch (e: Exception) {
                _userState.value = UserUIState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun extractErrorMessage(errorBody: String?): String {
        return try {
            val jsonObject = JSONObject(errorBody ?: "")
            jsonObject.optString("message", "Something went wrong")
        } catch (e: Exception) {
            "Something went wrong"
        }
    }

    fun resetState() {
        _userState.value = UserUIState.Idle
    }
}