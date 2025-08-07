package com.devmare.lld_forge_app.ui.features.home.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.devmare.lld_forge_app.core.utils.customWidthSpace
import com.devmare.lld_forge_app.ui.theme.primaryTextColor

@Composable
fun UserProfile(name: String, role: String) {
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle with first letter
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().uppercaseChar().toString(),
                style = MaterialTheme.typography.titleLarge,
                color = primaryTextColor
            )
        }

        customWidthSpace(width = 12.dp)

        // Name and Role
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = primaryTextColor
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Role icon",
                    tint = primaryTextColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = role,
                    style = MaterialTheme.typography.bodySmall,
                    color = primaryTextColor
                )
            }
        }
    }
}