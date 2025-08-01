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
import com.devmare.lld_forge_app.ui.features.auth.SignUpScreen
import com.devmare.lld_forge_app.ui.theme.backgroundColor
import com.devmare.lld_forge_app.ui.features.auth.viewmodel.SignUpViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreenWrapper(
    onAlreadyHaveAccountClick: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {},
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentColor = backgroundColor,
        containerColor = backgroundColor
    ) { paddingValues ->
        SignUpScreen(
            modifier = Modifier.padding(paddingValues),
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope,
            onLoginClick = { name, email, password ->
                coroutineScope.launch {
                    try {
                        if (name.isBlank() || email.isBlank() || password.isBlank()) {
                            throw UserInputException("All fields are required")
                        }

                        viewModel.signup(name, email, password)
                    } catch (e: UserInputException) {
                        snackbarHostState.showSnackbar(e.message ?: "Input error")
                    } catch (e: Exception) {
                        snackbarHostState.showSnackbar("Unexpected error: ${e.message}")
                    }
                }
            },
            onAlreadyHaveAccountClick = onAlreadyHaveAccountClick,
            viewModel = viewModel,
            onSignupSuccess = onSignUpSuccess
        )
    }
}
