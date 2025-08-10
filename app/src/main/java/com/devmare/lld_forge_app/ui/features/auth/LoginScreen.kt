package com.devmare.lld_forge_app.ui.features.auth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devmare.lld_forge_app.core.common.AuthTextField
import com.devmare.lld_forge_app.core.common.ClickableLoginText
import com.devmare.lld_forge_app.core.common.GradientButton
import com.devmare.lld_forge_app.ui.features.auth.viewmodel.LoginViewModel
import com.devmare.lld_forge_app.ui.theme.primaryGradientMiddle
import com.devmare.lld_forge_app.ui.theme.primaryGradientStart
import com.devmare.lld_forge_app.ui.theme.primaryTextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier,
    onLoginClick: (email: String, password: String) -> Unit = { _, _ -> },
    onNavigateToSignUp: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val uiState = viewModel.state

    LaunchedEffect(uiState.accessToken, uiState.error) {
        if (!uiState.accessToken.isNullOrBlank()) {
            onLoginSuccess()
        } else if (uiState.error != null) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(uiState.error)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome Back!",
                style = MaterialTheme.typography.displayLarge,
                color = primaryTextColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                icon = Icons.Default.Email,
                keyboardType = KeyboardType.Email
            )

            AuthTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                icon = Icons.Default.Lock,
                keyboardType = KeyboardType.Password,
                isPassword = true
            )

            GradientButton(
                text = "Login",
                onClick = { onLoginClick(email, password) },
                gradientColors = listOf(primaryGradientStart, primaryGradientMiddle),
                enabled = !uiState.isLoading
            )

            ClickableLoginText(
                normalText = "Don't have an account? ",
                clickableText = "Sign Up",
                onClick = onNavigateToSignUp
            )
        }
    }
}