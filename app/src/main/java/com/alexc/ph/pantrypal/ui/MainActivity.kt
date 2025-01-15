package com.alexc.ph.pantrypal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexc.ph.pantrypal.ui.theme.PantryPalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberPantryPalAppState()
            PantryPalTheme {
                PantryPalApp(appState = appState)
            }
        }
    }
}
