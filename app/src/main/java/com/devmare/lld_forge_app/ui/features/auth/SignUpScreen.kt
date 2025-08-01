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
import androidx.compose.material.icons.filled.Person
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
import com.devmare.lld_forge_app.ui.common.AuthTextField
import com.devmare.lld_forge_app.ui.common.ClickableLoginText
import com.devmare.lld_forge_app.ui.common.GradientButton
import com.devmare.lld_forge_app.ui.theme.gradient1
import com.devmare.lld_forge_app.ui.theme.gradient2
import com.devmare.lld_forge_app.ui.theme.whiteColor
import com.devmare.lld_forge_app.ui.features.auth.viewmodel.SignUpUIState
import com.devmare.lld_forge_app.ui.features.auth.viewmodel.SignUpViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    modifier: Modifier,
    onLoginClick: (name: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onAlreadyHaveAccountClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    viewModel: SignUpViewModel = hiltViewModel(),
    onSignupSuccess: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    // Access uiState directly from ViewModel
    val uiState = viewModel.uiState.value

    // Snackbar handling side effect
    LaunchedEffect(uiState) {
        when (uiState) {
            is SignUpUIState.Success -> {
                snackbarHostState.showSnackbar(uiState.message)
                viewModel.reset()
                onSignupSuccess()
            }

            is SignUpUIState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.error)
                    viewModel.reset()
                }
            }

            else -> {}
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
            Text("LLD FORGE.", style = MaterialTheme.typography.displayLarge, color = whiteColor)

            Spacer(modifier = Modifier.height(16.dp))

            AuthTextField(
                value = name,
                onValueChange = { name = it },
                label = "Name",
                icon = Icons.Default.Person,
                keyboardType = KeyboardType.Text
            )

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
                text = if (uiState is SignUpUIState.Loading) "Signing Up..." else "Sign Up",
                onClick = {
                    if (uiState !is SignUpUIState.Loading) {
                        onLoginClick(name, email, password)
                    }
                },
                gradientColors = listOf(
                    gradient1,
                    gradient2
                ),
                enabled = uiState !is SignUpUIState.Loading
            )

            ClickableLoginText(
                normalText = "Already have an account? ",
                clickableText = "Login",
                onClick = onAlreadyHaveAccountClick
            )
        }
    }
}
