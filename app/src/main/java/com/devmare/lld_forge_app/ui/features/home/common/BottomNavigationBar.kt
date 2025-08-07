package com.devmare.lld_forge_app.ui.features.home.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devmare.lld_forge_app.ui.theme.primaryGradientEnd
import com.devmare.lld_forge_app.ui.theme.surfaceBorder

data class BottomNavItem(
    val label: String,
    @DrawableRes val iconRes: Int,
)

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(16.dp) // This adds margin from bottom and sides
    ) {
        NavigationBar(
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(surfaceBorder),
            containerColor = surfaceBorder
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = { onItemSelected(index) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.label,
                            tint = if (selectedIndex == index) primaryGradientEnd else Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(item.label) },
                    alwaysShowLabel = false
                )
            }
        }
    }
}
