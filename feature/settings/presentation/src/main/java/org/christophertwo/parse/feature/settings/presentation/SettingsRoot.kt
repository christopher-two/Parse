package org.christophertwo.parse.feature.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.domain.models.settings.Contrast
import org.christophertwo.parse.feature.navigation.navigator.GlobalNavigator
import org.koin.compose.koinInject

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    val globalNavigator = koinInject<GlobalNavigator>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            globalNavigator.back()
                        },
                        content = {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back"
                            )
                        }
                    )
                },
                title = { Text("Settings") },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsSection(title = "Apariencia") {
                SettingsSwitch(
                    title = "Tema del sistema",
                    checked = state.themeSystem,
                    onCheckedChange = { checked ->
                        onAction(SettingsAction.SetThemeSystem(checked))
                    }
                )
                SettingsSwitch(
                    title = "Tema oscuro",
                    checked = state.darkTheme,
                    enabled = !state.themeSystem,
                    onCheckedChange = { checked ->
                        onAction(SettingsAction.SetDarkTheme(checked))
                    }
                )

                SettingsSwitch(
                    title = "AMOLED (negros puros)",
                    checked = state.amoledTheme,
                    onCheckedChange = { checked ->
                        onAction(SettingsAction.SetAmoledTheme(checked))
                    }
                )

                SeedColorSettings(
                    title = "Color del tema",
                    selectedColor = state.themeColor,
                    onSelected = { colorInt ->
                        onAction(SettingsAction.SetThemeColor(colorInt))
                    }
                )
            }

            SettingsSection(title = "Accesibilidad") {
                MenuSettings(
                    title = "Contraste",
                    items = Contrast.entries,
                    selectedItem = state.contrast,
                    itemLabel = { it.labelEs() },
                    enabled = true,
                    onSelected = { contrast ->
                        onAction(SettingsAction.SetContrast(contrast))
                    }
                )

                FontSizeSlider(
                    title = "Tamaño de texto",
                    value = state.fontSize,
                    valueRange = 0.85f..1.30f,
                    steps = 8,
                    onValueChange = { newValue ->
                        onAction(SettingsAction.SetFontSize(newValue))
                    }
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 4.dp)
        )

        content()
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsSwitch(
    title: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer,
            contentColor = colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.extraLargeIncreased
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)
            Switch(
                enabled = enabled,
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colorScheme.primaryContainer,
                    checkedTrackColor = colorScheme.onPrimaryContainer,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> MenuSettings(
    title: String,
    items: List<T>,
    selectedItem: T,
    itemLabel: (T) -> String = { it.toString() },
    enabled: Boolean,
    onSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer,
            contentColor = colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.extraLargeIncreased
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title)

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    if (enabled) {
                        expanded = !expanded
                    }
                }
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    value = itemLabel(selectedItem),
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = itemLabel(selectionOption)) },
                            onClick = {
                                onSelected(selectionOption)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun SeedColorSettings(
    title: String,
    selectedColor: Int,
    onSelected: (Int) -> Unit
) {
    val palette = remember {
        listOf(
            0xFF2D6A4F.toInt(), // Forest
            0xFF1B4965.toInt(), // Deep blue
            0xFF6D597A.toInt(), // Mauve
            0xFFB23A48.toInt(), // Brick rose
            0xFFE09F3E.toInt(), // Warm amber
            0xFF4D908E.toInt(), // Teal soft
            0xFF3A5A40.toInt(), // Olive
            0xFF5E548E.toInt(), // Indigo
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer,
            contentColor = colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.extraLargeIncreased
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = title)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                palette.forEach { colorInt ->
                    val isSelected = colorInt == selectedColor
                    val outline = if (isSelected) colorScheme.primary else colorScheme.outlineVariant

                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 36.dp else 32.dp)
                            .background(Color(colorInt), CircleShape)
                            .clickable { onSelected(colorInt) }
                            .padding(2.dp)
                            .background(outline, CircleShape)
                            .padding(2.dp)
                            .background(Color(colorInt), CircleShape)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FontSizeSlider(
    title: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    onValueChange: (Float) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainer,
            contentColor = colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.extraLargeIncreased
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title)
                Text(text = String.format("%.0f%%", value * 100f))
            }

            Slider(
                value = value.coerceIn(valueRange),
                onValueChange = { onValueChange(it) },
                valueRange = valueRange,
                steps = steps,
            )
        }
    }
}

private fun Contrast.labelEs(): String = when (this) {
    Contrast.LOW -> "Bajo"
    Contrast.MEDIUM -> "Medio"
    Contrast.HIGH -> "Alto"
}

@Preview
@Composable
private fun Preview() {
    ParseTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}