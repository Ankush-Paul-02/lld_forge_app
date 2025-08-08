package com.devmare.lld_forge_app.ui.features.home.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devmare.lld_forge_app.ui.theme.primaryGradientEnd
import com.devmare.lld_forge_app.ui.theme.surfaceBorder
import com.devmare.lld_forge_app.ui.theme.transparent

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
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
            .shadow(elevation = 12.dp, shape = RoundedCornerShape(32.dp), clip = false)
            .clip(RoundedCornerShape(32.dp))
            .background(transparent)
    ) {
        NavigationBar(
            tonalElevation = 0.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(Color.Transparent),
            containerColor = surfaceBorder
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onItemSelected(index) },
                    icon = {
                        androidx.compose.animation.AnimatedContent(
                            targetState = isSelected,
                            label = "BottomNavIconAnimation"
                        ) { selected ->
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.label,
                                tint = if (selected) primaryGradientEnd else Color.Gray,
                                modifier = Modifier
                                    .size(if (selected) 28.dp else 24.dp)
                            )
                        }
                    },
                    label = {
                        androidx.compose.animation.AnimatedVisibility(visible = isSelected) {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelSmall,
                                color = primaryGradientEnd
                            )
                        }
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}