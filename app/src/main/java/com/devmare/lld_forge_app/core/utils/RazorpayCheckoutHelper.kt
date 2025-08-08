package com.devmare.lld_forge_app.core.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.razorpay.Checkout
import org.json.JSONObject

class RazorpayCheckoutHelper(
    private val context: Context,
    private val orderId: String,
    private val amount: Int,
    private val userName: String,
    private val userEmail: String,
    private val userContact: String,
) {

    fun startPayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_2uMnnYIqqjz7aT")

        try {
            val options = JSONObject().apply {
                put("name", userName)
                put("description", "Mentorship Booking")
                put("order_id", orderId)
                put("currency", "INR")
                put("amount", amount)

                put("prefill", JSONObject().apply {
                    put("email", userEmail)
                    put("contact", userContact)
                })
            }

            checkout.open(context as Activity, options)
        } catch (e: Exception) {
            Log.e("Razorpay", "Error in starting Razorpay Checkout", e)
        }
    }
}
