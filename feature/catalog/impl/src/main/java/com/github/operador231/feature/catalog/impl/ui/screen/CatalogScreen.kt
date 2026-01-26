package com.github.operador231.feature.catalog.impl.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.operador231.core.ui.theme.AniFluxTheme
import com.github.operador231.feature.catalog.impl.R
import com.github.operador231.feature.catalog.impl.ui.viewmodel.CatalogUiState
import com.github.operador231.feature.catalog.impl.ui.viewmodel.CatalogViewModel

@Composable
internal fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CatalogTopBar(scrollBehavior) }
    ) { innerPaddings ->
        CatalogContent(contentPadding = innerPaddings)
    }
}

@Composable
private fun CatalogTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.st_catalog)
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
@PreviewScreenSizes
private fun CatalogContent(
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(contentPadding)
            .padding(horizontal = AniFluxTheme.paddings.medium)
    ) {
        repeat(70) {
            item { Text("Ça marche !") }
        }
    }
}