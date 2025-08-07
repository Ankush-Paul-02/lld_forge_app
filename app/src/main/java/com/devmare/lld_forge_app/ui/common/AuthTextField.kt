package com.devmare.lld_forge_app.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.devmare.lld_forge_app.R
import com.devmare.lld_forge_app.ui.theme.primaryGradientMiddle
import com.devmare.lld_forge_app.ui.theme.primaryTextColor
import com.devmare.lld_forge_app.ui.theme.secondaryTextColor

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryGradientMiddle,
            unfocusedBorderColor = secondaryTextColor,
            disabledTextColor = primaryTextColor,
            focusedTextColor = primaryTextColor,
            unfocusedTextColor = primaryTextColor,
            cursorColor = primaryTextColor,
            focusedLabelColor = primaryTextColor,
            unfocusedLabelColor = secondaryTextColor
        ),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
        trailingIcon = {
            if (isPassword) {
                val iconRes =
                    if (passwordVisible) R.drawable.password_view else R.drawable.password_hide
                val description = if (passwordVisible) "Hide password" else "Show password"
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = description,
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(secondaryTextColor),
                    modifier = Modifier
                        .clickable { passwordVisible = !passwordVisible }
                        .size(24.dp)
                )
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        )
    )
}