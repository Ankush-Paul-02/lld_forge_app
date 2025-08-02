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
import com.devmare.lld_forge_app.ui.features.home.viewmodel.HomeUIState
import com.devmare.lld_forge_app.ui.features.home.viewmodel.HomeViewModel
import com.devmare.lld_forge_app.ui.theme.gradient2

@Composable
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onSessionExpired: () -> Unit = {},
) {
    val state by homeViewModel.homeState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchCurrentUserProfile()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is HomeUIState.Loading -> {
                CircularProgressIndicator(color = gradient2)
            }

            is HomeUIState.Success -> {
                val user = (state as HomeUIState.Success).user
                Text(
                    text = "Welcome, ${user.name} / ${user.email}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = gradient2,
                    textAlign = TextAlign.Center
                )
            }

            is HomeUIState.Error -> {
                val message = (state as HomeUIState.Error).message
                Text(
                    text = "Error: $message",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            is HomeUIState.SessionExpired -> {
                // Call the passed-in logout navigation handler
                onSessionExpired()
                Text(
                    text = "Session expired. Redirecting to login...",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            HomeUIState.Idle -> {
                Text(
                    text = "Loading user...",
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
