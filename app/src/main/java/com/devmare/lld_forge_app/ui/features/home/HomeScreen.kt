package com.devmare.lld_forge_app.ui.features.home

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.devmare.lld_forge_app.ui.theme.gradient2
import com.devmare.lld_forge_app.ui.features.home.viewmodel.UserViewModel
import com.devmare.lld_forge_app.ui.features.home.viewmodel.UserUIState

@Composable
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun HomeScreen(
    userViewModel: UserViewModel = hiltViewModel(),
) {

    val userState by userViewModel.userState.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.fetchCurrentUserProfile()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = userState) {
            is UserUIState.Success -> {
                Text(
                    text = "Welcome, ${state.user.name + "/" + state.user.email}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = gradient2,
                    textAlign = TextAlign.Center
                )
            }
            is UserUIState.Loading -> {
                CircularProgressIndicator(color = gradient2)
            }
            is UserUIState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
            else -> {
                Text(
                    text = "Loading user...",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
