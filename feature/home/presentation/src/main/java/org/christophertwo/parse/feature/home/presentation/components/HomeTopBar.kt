package org.christophertwo.parse.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.christophertwo.parse.core.common.route.RouteGlobal
import org.christophertwo.parse.feature.navigation.navigator.GlobalNavigator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun HomeTopBar(globalNavigator: GlobalNavigator) {
    TopAppBar(
        title = { Text("Parse") },
        actions = {
            IconButton(
                onClick = {
                    globalNavigator.navigateTo(RouteGlobal.Settings)
                },
                content = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = colorScheme.primaryContainer,
                                shape = MaterialShapes.Pentagon.toShape()
                            ),
                        contentAlignment = Alignment.Center,
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings",
                                tint = colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            )
        }
    )
}