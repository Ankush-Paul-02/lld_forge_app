package com.devmare.lld_forge_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.devmare.lld_forge_app.core.navigation.LldForgeNavGraph
import com.devmare.lld_forge_app.core.navigation.Screen
import com.devmare.lld_forge_app.core.prefs.DataStoreManager
import com.devmare.lld_forge_app.ui.features.home.viewmodel.MentorshipViewModel
import com.devmare.lld_forge_app.ui.theme.LldForgeAppTheme
import com.devmare.lld_forge_app.ui.theme.appBackground
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultListener {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private lateinit var mentorshipViewModel: MentorshipViewModel

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Checkout.preload(applicationContext)
        enableEdgeToEdge()

        setContent {
            LldForgeAppTheme {
                val navController = rememberNavController()
                mentorshipViewModel = viewModel()

                val startDestination = remember { mutableStateOf<String?>(null) }

                // Decide initial screen
                LaunchedEffect(Unit) {
                    val token = dataStoreManager.getAccessToken()
                    startDestination.value =
                        if (token != null) Screen.HOME.name else Screen.AUTH.name
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(appBackground)
                ) {
                    if (startDestination.value == null) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else {
                        LldForgeNavGraph(navController, startDestination.value!!)
                    }
                }
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        mentorshipViewModel.emitPaymentSuccess(razorpayPaymentId ?: "Unknown ID")
    }

    override fun onPaymentError(code: Int, response: String?) {
        mentorshipViewModel.emitPaymentError(response ?: "Unknown error")
    }
}
