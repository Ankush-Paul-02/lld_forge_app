package com.devmare.lld_forge_app.ui.common

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.devmare.lld_forge_app.ui.theme.gradient2
import com.devmare.lld_forge_app.ui.theme.whiteColor

@Composable
fun ClickableLoginText(
    normalText: String,
    clickableText: String,
    onClick: () -> Unit,
) {
    val annotatedString = buildAnnotatedString {
        append(normalText)
        pushStringAnnotation(tag = "LOGIN", annotation = "login")
        withStyle(
            style = SpanStyle(
                color = gradient2,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(clickableText)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge.copy(color = whiteColor),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                .firstOrNull()?.let {
                    onClick()
                }
        },
    )
}
