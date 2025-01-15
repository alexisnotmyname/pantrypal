package com.alexc.ph.pantrypal.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexc.ph.pantrypal.R
import com.alexc.ph.pantrypal.ui.theme.PantryPalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantryPalTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    navigation: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = { navigation() },
        actions = { action() },
        colors = colors,
        modifier = modifier
    )
}

@Composable
fun GenericTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigation: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigation()
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
        action()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun NiaTopAppBarPreview() {
    PantryPalTheme(dynamicColor = false) {
        PantryPalTopAppBar(
            title = stringResource(android.R.string.untitled),
            navigation = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = PantryPalIcons.Back,
                        contentDescription = "Navigation icon",
                    )
                }
            },
            action = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = PantryPalIcons.Forward,
                        contentDescription = "Action icon",
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenericTopAppBarPreview() {
    PantryPalTheme(dynamicColor = false) {
        GenericTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = "Preview",
            navigation = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = PantryPalIcons.Back,
                        contentDescription = null
                    )
                }
            },
            action = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = PantryPalIcons.Forward,
                        contentDescription = null
                    )
                }
            },
        )
    }
}

