package com.devmare.lld_forge_app.ui.features.home.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.devmare.lld_forge_app.core.dto.MentorDto
import com.devmare.lld_forge_app.core.utils.RazorpayCheckoutHelper
import com.devmare.lld_forge_app.core.utils.customHeightSpacer
import com.devmare.lld_forge_app.core.utils.customWidthSpace
import com.devmare.lld_forge_app.data.model.MentorshipBookingRequest
import com.devmare.lld_forge_app.domain.model.User
import com.devmare.lld_forge_app.ui.features.home.viewmodel.BookSessionUiState
import com.devmare.lld_forge_app.ui.features.home.viewmodel.MentorshipViewModel
import com.devmare.lld_forge_app.ui.theme.primaryGradientEnd
import com.devmare.lld_forge_app.ui.theme.primaryGradientMiddle
import com.devmare.lld_forge_app.ui.theme.primaryTextColor
import com.devmare.lld_forge_app.ui.theme.whiteColor
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun MentorList(mentors: List<MentorDto>, user: User) {
    var expandedMentor by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    Column(modifier = Modifier.padding(top = 24.dp)) {
        Text(
            text = "Top Mentors",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
            color = primaryGradientEnd
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(mentors) { mentor ->
                MentorItem(
                    mentor = mentor,
                    user = user,
                    isExpanded = expandedMentor == mentor.name,
                    onClick = {
                        expandedMentor = if (expandedMentor == mentor.name) null else mentor.name
                    },
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MentorItem(
    mentor: MentorDto,
    user: User,
    isExpanded: Boolean,
    onClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val calendar = Calendar.getInstance()
    val mentorshipViewModel: MentorshipViewModel = hiltViewModel()
    val bookingState by mentorshipViewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedYear by remember { mutableIntStateOf(0) }
    var selectedMonth by remember { mutableIntStateOf(0) }
    var selectedDay by remember { mutableIntStateOf(0) }
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var selectedPayment by remember { mutableStateOf("30_min") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month
            selectedDay = dayOfMonth
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedHour = hourOfDay
            selectedMinute = minute
            val hour12 = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
            val amPm = if (hourOfDay < 12) "AM" else "PM"
            selectedTime = String.format("%d:%02d %s", hour12, minute, amPm)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        false,
    )

    LaunchedEffect(Unit) {
        mentorshipViewModel.paymentResult.collect { result ->
            result.onSuccess {
                snackbarHostState.showSnackbar("Payment Success")
            }.onFailure {
                snackbarHostState.showSnackbar("Payment Failed")
            }
        }
    }

    // Only collect once safely across recompositions
    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mentorshipViewModel.paymentResult.collect { result ->
                result.onSuccess { paymentId ->
                    Toast.makeText(
                        context,
                        "Payment Success: $paymentId",
                        Toast.LENGTH_LONG
                    ).show()
                    // Navigate to next screen or refresh UI
                }.onFailure { ex ->
                    Toast.makeText(
                        context,
                        "Payment Failed: ${ex.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    // Show error UI or allow retry
                }
            }
        }
    }

    LaunchedEffect(bookingState) {
        if (bookingState is BookSessionUiState.Success) {
            val data = (bookingState as BookSessionUiState.Success).data
            val activity = context as? Activity
            if (activity != null) {
                RazorpayCheckoutHelper(
                    context = activity,
                    orderId = data.orderId,
                    amount = data.amount,
                    userName = user.name,
                    userEmail = user.email,
                    userContact = "6009036778"
                ).startPayment()
            }
            mentorshipViewModel.resetState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = mentor.name.firstOrNull()?.uppercase() ?: "?",
                    style = MaterialTheme.typography.titleLarge,
                    color = primaryTextColor
                )
            }

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = mentor.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = whiteColor
                )
                customHeightSpacer(8.dp)
                Text(
                    text = "Questions shared: ${mentor.questionCount}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = whiteColor
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 8.dp, bottom = 16.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(primaryGradientEnd)
                )

                customWidthSpace(40.dp)

                Column {
                    customHeightSpacer(4.dp)

                    Text("Available: Mon, Wed, Fri", color = whiteColor)

                    customHeightSpacer(8.dp)

                    Button(
                        onClick = { datePickerDialog.show() },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryGradientMiddle)
                    ) {
                        Text(text = if (selectedDate.isEmpty()) "Select Date" else "Date: $selectedDate")
                    }

                    customHeightSpacer(8.dp)

                    Button(
                        onClick = { timePickerDialog.show() },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryGradientMiddle)
                    ) {
                        Text(text = if (selectedTime.isEmpty()) "Select Time" else "Time: $selectedTime")
                    }

                    customHeightSpacer(12.dp)

                    Text("Select Session:", color = whiteColor)

                    Column {
                        RadioButtonRow("30_min", "30 mins - ₹99", selectedPayment) {
                            selectedPayment = it
                        }
                        RadioButtonRow("45_min", "45 mins - ₹149", selectedPayment) {
                            selectedPayment = it
                        }
                        RadioButtonRow("60_min", "60 mins - ₹199", selectedPayment) {
                            selectedPayment = it
                        }
                    }

                    customHeightSpacer(16.dp)

                    Button(
                        onClick = {
                            if (selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                                val cal = Calendar.getInstance().apply {
                                    set(Calendar.YEAR, selectedYear)
                                    set(Calendar.MONTH, selectedMonth)
                                    set(Calendar.DAY_OF_MONTH, selectedDay)
                                    set(Calendar.HOUR_OF_DAY, selectedHour)
                                    set(Calendar.MINUTE, selectedMinute)
                                    set(Calendar.SECOND, 0)
                                    set(Calendar.MILLISECOND, 0)
                                }

                                val request = MentorshipBookingRequest(
                                    amount = when (selectedPayment) {
                                        "30_min" -> 99
                                        "45_min" -> 149
                                        "60_min" -> 199
                                        else -> 99
                                    },
                                    receiverId = mentor.id,
                                    scheduledAt = cal.timeInMillis,
                                    durationInMinutes = when (selectedPayment) {
                                        "30_min" -> 30
                                        "45_min" -> 45
                                        "60_min" -> 60
                                        else -> 30
                                    }
                                )

                                mentorshipViewModel.bookSession(request)

                                // Reset
                                selectedDate = ""
                                selectedTime = ""
                                selectedPayment = "30_min"
                                onClick()
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("📅 Please select both date and time.")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryGradientMiddle,
                            contentColor = whiteColor
                        )
                    ) {
                        Text(text = "Book Session")
                    }

                    customHeightSpacer(16.dp)
                }
            }
        }
    }
}


@Composable
fun RadioButtonRow(
    value: String,
    label: String,
    selectedValue: String,
    onSelected: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelected(value) }
    ) {
        RadioButton(
            selected = selectedValue == value,
            onClick = { onSelected(value) },
            colors = RadioButtonDefaults.colors(selectedColor = primaryGradientMiddle)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, color = whiteColor)
    }
}
