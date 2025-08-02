package com.devmare.lld_forge_app.core.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devmare.lld_forge_app.ui.features.auth.wrapper.LoginScreenWrapper
import com.devmare.lld_forge_app.ui.features.auth.wrapper.SignUpScreenWrapper
import com.devmare.lld_forge_app.ui.features.home.HomeScreen

enum class Screen {
    AUTH,
    LOGIN,
    HOME
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
fun LldForgeNavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.AUTH.name) {
            SignUpScreenWrapper(
                onSignUpSuccess = {
                    navController.navigate(Screen.LOGIN.name)
                },
                onAlreadyHaveAccountClick = {
                    navController.navigate(Screen.LOGIN.name)
                }
            )
        }

        composable(Screen.LOGIN.name) {
            LoginScreenWrapper(
                onLoginSuccess = {
                    navController.navigate(Screen.HOME.name) {
                        popUpTo(Screen.AUTH.name) { inclusive = true } /// Remove auth screen from backstack
                    }
                },
                onSignUpNavigate = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.HOME.name) {
            HomeScreen(
                onSessionExpired = {
                    navController.navigate(Screen.LOGIN.name) {
                        popUpTo(Screen.HOME.name) { inclusive = true }
                    }
                }
            )
        }
    }
}
