package com.devmare.lld_forge_app.ui.features.home.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.devmare.lld_forge_app.core.common.UiState
import com.devmare.lld_forge_app.core.dto.MentorshipSessionResponseDto
import com.devmare.lld_forge_app.domain.model.User
import com.devmare.lld_forge_app.ui.features.home.viewmodel.MentorshipViewModel

@Composable
fun MentorshipSession(
    user: User,
    mentorshipViewModel: MentorshipViewModel,
) {
    val sessionsState by mentorshipViewModel.sessionsState.collectAsState()

    LaunchedEffect(Unit) {
        mentorshipViewModel.fetchSessions()
    }
    when (sessionsState) {
        is UiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UiState.Success -> {
            val sessions = (sessionsState as UiState.Success).data
            SessionsList(sessions) // your own composable
        }

        is UiState.Error -> Text(
            text = (sessionsState as UiState.Error).message,
            color = MaterialTheme.colorScheme.error
        )

        UiState.Idle -> Text("Loading sessions...")
    }
}

@Composable
fun SessionsList(sessions: List<MentorshipSessionResponseDto>) {
    Column {
        sessions.forEach { session ->
            Text(text = "Session with ${session.mentorName} at ${session.mentorshipSessionStatus}")
        }
    }
}
