package com.devmare.lld_forge_app.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.devmare.lld_forge_app.R
import com.devmare.lld_forge_app.core.navigation.Screen
import com.devmare.lld_forge_app.core.prefs.DataStoreManager
import com.devmare.lld_forge_app.ui.features.home.common.BottomNavItem
import com.devmare.lld_forge_app.ui.features.home.common.BottomNavigationBar
import com.devmare.lld_forge_app.ui.features.home.common.MentorList
import com.devmare.lld_forge_app.ui.features.home.common.MentorshipSession
import com.devmare.lld_forge_app.ui.features.home.common.UserProfile
import com.devmare.lld_forge_app.ui.features.home.viewmodel.HomeUIState
import com.devmare.lld_forge_app.ui.features.home.viewmodel.HomeViewModel
import com.devmare.lld_forge_app.ui.features.home.viewmodel.MentorshipViewModel
import com.devmare.lld_forge_app.ui.theme.primaryGradientMiddle
import com.devmare.lld_forge_app.ui.theme.surfaceBorder
import kotlinx.coroutines.launch

@Composable
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalLayoutApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    mentorshipViewModel: MentorshipViewModel = hiltViewModel(),
    onSessionExpired: () -> Unit = {},
) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val state by homeViewModel.homeState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val isImeVisible = WindowInsets.isImeVisible
    val navController = rememberNavController()
    val lifecycleOwner = LocalLifecycleOwner.current

    val bottomNavItems = listOf(
        BottomNavItem("Home", R.drawable.home),
        BottomNavItem("Sessions", R.drawable.session),
        BottomNavItem("Community", R.drawable.communities),
        BottomNavItem("Question", R.drawable.question),
    )

    LaunchedEffect(Unit) {
        homeViewModel.fetchCurrentUserProfile()
    }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mentorshipViewModel.paymentResult.collect { result ->
                result.onSuccess { paymentId ->
                    navController.navigate(Screen.HOME.name) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }.onFailure { }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            // Hide nav bar when keyboard is open
            if (!isImeVisible) {
                BottomNavigationBar(
                    items = bottomNavItems,
                    selectedIndex = selectedTab,
                    onItemSelected = { selectedTab = it }
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            when (state) {
                is HomeUIState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = primaryGradientMiddle)
                    }
                }

                is HomeUIState.Success -> {
                    val user = (state as HomeUIState.Success).user
                    val topMentors = (state as HomeUIState.Success).topMentors

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 0.dp),
                        verticalArrangement = Arrangement.Top
                    ) {
                        // Top Bar Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            UserProfile(user.name, user.role)

                            var menuExpanded by remember { mutableStateOf(false) }

                            Box {
                                IconButton(onClick = { menuExpanded = true }) {
                                    Icon(
                                        imageVector = Icons.Default.MoreVert,
                                        contentDescription = "More options",
                                        tint = primaryGradientMiddle
                                    )
                                }

                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false },
                                    modifier = Modifier.background(surfaceBorder)
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("Logout") },
                                        onClick = {
                                            menuExpanded = false
                                            coroutineScope.launch {
                                                dataStoreManager.clearAccessToken()
                                                onSessionExpired()
                                            }
                                        },
                                        leadingIcon = {
                                            Icon(
                                                painter = painterResource(id = R.drawable.logout),
                                                contentDescription = "Logout",
                                                tint = Color.Gray,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        // Flowing content based on tab
                        when (selectedTab) {
                            0 -> MentorList(topMentors, user = user, navController = navController)
                            1 -> MentorshipSession(
                                user = user,
                                mentorshipViewModel = mentorshipViewModel
                            )

                            2 -> Text(
                                text = "Community section coming soon",
                                modifier = Modifier.padding(top = 32.dp)
                            )

                            3 -> Text(
                                text = "Question section coming soon",
                                modifier = Modifier.padding(top = 32.dp)
                            )
                        }
                    }
                }

                is HomeUIState.Error -> {
                    val message = (state as HomeUIState.Error).message
                    Text(
                        text = "Error: $message",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                is HomeUIState.SessionExpired -> {
                    LaunchedEffect(Unit) {
                        dataStoreManager.clearAccessToken()
                        onSessionExpired()
                    }
                    onSessionExpired()
                    Text(
                        text = "Session expired. Redirecting...",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                HomeUIState.Idle -> {
                    Text(
                        text = "Loading user...",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}