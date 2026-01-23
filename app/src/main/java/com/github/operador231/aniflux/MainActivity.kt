package com.github.operador231.aniflux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.github.operador231.core.ui.theme.AniFluxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AniFluxTheme {
                AniFluxApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
public fun AniFluxApp() {
    NavigationSuiteScaffold(
        navigationSuiteItems = {

        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        }
    }
}
