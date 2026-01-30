package com.github.operador231.feature.catalog.impl.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.operador231.core.data.mapper.toAppException
import com.github.operador231.core.data.mapper.toDomain
import com.github.operador231.core.domain.model.Media
import com.github.operador231.core.ui.annotations.ExperimentalAniFluxUi
import com.github.operador231.core.ui.component.ConnectivityIndicator
import com.github.operador231.core.ui.extensions.getErrorMessage
import com.github.operador231.core.ui.screen.StateScreen
import com.github.operador231.core.ui.theme.AniFluxTheme
import com.github.operador231.core.ui.utils.LocalWindowSizeClass
import com.github.operador231.core.ui.utils.ObserveAsEvents
import com.github.operador231.feature.catalog.impl.R
import com.github.operador231.feature.catalog.impl.ui.component.PreviewDefaults
import com.github.operador231.feature.catalog.impl.ui.component.PreviewGridItem
import com.github.operador231.feature.catalog.impl.ui.component.PreviewListItem
import com.github.operador231.feature.catalog.impl.ui.viewmodel.CatalogUiEffect
import com.github.operador231.feature.catalog.impl.ui.viewmodel.CatalogViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import com.github.operador231.core.ui.R as RCore

@Composable
internal fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val anime = viewModel.anime.collectAsLazyPagingItems()
    val loadState = anime.loadState.mediator?.refresh ?: anime.loadState.refresh
    val snackbarHostState = remember { SnackbarHostState() }

    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()

    val isError = loadState is LoadState.Error
    var errorMessage by remember { mutableStateOf("") }
    val contentIsEmpty = anime.loadState.source.refresh is LoadState.NotLoading && anime.itemCount == 0
    val isInitialLoading = anime.loadState.source.refresh is LoadState.Loading && anime.itemCount == 0
    val isRefreshing = anime.loadState.mediator?.refresh is LoadState.Loading

    LaunchedEffect(loadState) {
        if (isError) viewModel.onError(loadState.error)
        else Timber.d(loadState.toString())
    }

    ObserveAsEvents(viewModel.uiEffect) { effect ->
        when (effect) {
            is CatalogUiEffect.OnNavigate -> {}
            is CatalogUiEffect.OnError -> {
                errorMessage = effect.error.getErrorMessage(ctx)
                scope.launch {
                    snackbarHostState.showSnackbar(message = errorMessage)
                }
            }
            is CatalogUiEffect.OnRetry -> anime.refresh()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CatalogTopBar(scrollBehavior) },
        snackbarHost = { CatalogSnackBarHost(snackbarHostState) }
    ) { innerPaddings ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPaddings)
        ) {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { anime.refresh() },
                modifier = Modifier.weight(1f)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    when {
                        isInitialLoading -> StateScreen(
                            content = { LoadingIndicator() },
                            userScrollEnabled = false
                        )
                        isError && anime.itemCount == 0 -> StateScreen(
                            text = errorMessage,
                            action = {
                                TextButton(onClick = viewModel::onRetry) {
                                    Text(stringResource(RCore.string.st_retry))
                                }
                            }
                        )
                        contentIsEmpty && !isRefreshing -> StateScreen(
                            content = { Text(stringResource(RCore.string.st_no_content)) },
                            userScrollEnabled = true
                        )
                        else -> CatalogContent(content = anime)
                    }
                }
            }

            ConnectivityIndicator(networkStatus = networkStatus)
        }
    }
}

@Composable
private fun CatalogTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.st_catalog)) },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun CatalogSnackBarHost(
    state: SnackbarHostState
) {
    SnackbarHost(
        hostState = state,
        snackbar = { data ->
            Snackbar(
                content = { Text(text = data.visuals.message) },
                dismissAction = {
                    TextButton(onClick = data::dismiss) {
                        Text(text = stringResource(R.string.st_ok))
                    }
                },
                modifier = Modifier.padding(horizontal = AniFluxTheme.paddings.medium)
            )
        }
    )
}

@OptIn(ExperimentalAniFluxUi::class)
@Composable
private fun CatalogContent(
    content: LazyPagingItems<Media>,
    contentPadding: PaddingValues = PaddingValues()
) {
    val sizeClass = LocalWindowSizeClass.current
    when (sizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> ListContent(content = content)
        else -> GridContent(content = content)
    }
}

@OptIn(ExperimentalAniFluxUi::class)
@Composable
private fun ListContent(content: LazyPagingItems<Media>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AniFluxTheme.paddings.medium),
        verticalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.small)
    ) {
        item { Spacer(modifier = Modifier.padding(top = AniFluxTheme.paddings.small)) }
        items(
            count = content.itemCount,
            key = content.itemKey { it.id.value.toInt() }
        ) { idx ->
            content[idx]?.let {
                PreviewListItem(item = it)
            }
        }
    }
}

@OptIn(ExperimentalAniFluxUi::class)
@Composable
private fun GridContent(content: LazyPagingItems<Media>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(PreviewDefaults.PosterWidth),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AniFluxTheme.paddings.medium),
        verticalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.small),
        horizontalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.small)
    ) {
        items(
            count = content.itemCount,
            key = content.itemKey { it.id.value.toInt() }
        ) { idx ->
            content[idx]?.let {
                PreviewGridItem(
                    item = it
                )
            }
        }
    }
}