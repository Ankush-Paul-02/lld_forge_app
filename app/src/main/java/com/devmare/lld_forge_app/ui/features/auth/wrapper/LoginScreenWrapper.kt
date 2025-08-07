package com.devmare.lld_forge_app.ui.features.auth.wrapper

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.devmare.lld_forge_app.core.exception.UserInputException
import com.devmare.lld_forge_app.ui.features.auth.LoginScreen
import com.devmare.lld_forge_app.ui.features.auth.viewmodel.LoginViewModel
import com.devmare.lld_forge_app.ui.theme.appBackground
import kotlinx.coroutines.launch

@Composable
fun LoginScreenWrapper(
    onSignUpNavigate: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentColor = appBackground,
        containerColor = appBackground
    ) { padding ->
        LoginScreen(
            modifier = Modifier.padding(padding),
            onLoginClick = { email, password ->
                try {
                    if (email.isBlank() || password.isBlank()) {
                        throw UserInputException("Please fill in all fields.")
                    }
                    // Add login logic here if needed
                    viewModel.login(email, password)
                } catch (e: UserInputException) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(e.message ?: "Invalid input")
                    }
                } catch (e: Exception) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Unexpected error: ${e.message}")
                    }
                }
            },
            onNavigateToSignUp = onSignUpNavigate,
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            onLoginSuccess = onLoginSuccess
        )
    }
}
