package com.devmare.lld_forge_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.devmare.lld_forge_app.core.prefs.DataStoreManager
import com.devmare.lld_forge_app.core.navigation.LldForgeNavGraph
import com.devmare.lld_forge_app.core.navigation.Screen
import com.devmare.lld_forge_app.ui.theme.Lld_forge_appTheme
import com.devmare.lld_forge_app.ui.theme.backgroundColor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lld_forge_appTheme {

                val navController = rememberNavController()
                val startDestination = remember { mutableStateOf<String?>(null) }

                /// Launch effect to check if user is logged in or not
                LaunchedEffect(Unit) {
                    val token = dataStoreManager.getAccessToken()
                    startDestination.value =
                        if (token != null) Screen.HOME.name else Screen.AUTH.name
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                ) {
                    /// Render NavGraph only when the destination is known
                    if (startDestination.value == null) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LldForgeNavGraph(navController, startDestination.value!!)
                    }
                }
            }
        }
    }
}
