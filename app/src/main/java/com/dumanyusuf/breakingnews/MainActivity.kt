package com.dumanyusuf.breakingnews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import com.dumanyusuf.breakingnews.presentation.view.HomePageView
import com.dumanyusuf.breakingnews.ui.theme.BreakingNewsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BreakingNewsTheme {
                Scaffold {
                    HomePageView()
                }
            }
        }
    }
}

