package com.example.showmethecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.showmethecard.ui.theme.ShowMeTheCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowMeTheCardTheme {
                MainScreen()
            }
        }
    }

}