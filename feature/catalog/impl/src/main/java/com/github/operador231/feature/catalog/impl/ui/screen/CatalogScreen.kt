package com.github.operador231.feature.catalog.impl.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.operador231.core.data.mapper.toAppException
import com.github.operador231.core.data.mapper.toDomain
import com.github.operador231.core.domain.model.Media
import com.github.operador231.core.ui.annotations.ExperimentalAniFluxUi
import com.github.operador231.core.ui.base.ObserveAsEvents
import com.github.operador231.core.ui.extensions.getErrorMessage
import com.github.operador231.core.ui.theme.AniFluxTheme
import com.github.operador231.core.ui.utils.LocalWindowSizeClass
import com.github.operador231.feature.catalog.impl.R
import com.github.operador231.feature.catalog.impl.ui.component.PreviewDefaults
import com.github.operador231.feature.catalog.impl.ui.component.PreviewGridItem
import com.github.operador231.feature.catalog.impl.ui.component.PreviewListItem
import com.github.operador231.feature.catalog.impl.ui.viewmodel.CatalogUiEffect
import com.github.operador231.feature.catalog.impl.ui.viewmodel.CatalogViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

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

    LaunchedEffect(loadState) {
        if (loadState is LoadState.Error) viewModel.onError(loadState.error)
        else Timber.d(loadState.toString())
    }

    ObserveAsEvents(viewModel.uiEffect) { effect ->
        when (effect) {
            is CatalogUiEffect.OnNavigate -> {}
            is CatalogUiEffect.OnError -> {
                val message = effect.error.getErrorMessage(ctx)
                scope.launch {
                    snackbarHostState.showSnackbar(message = message)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CatalogTopBar(scrollBehavior) },
        snackbarHost = { CatalogSnackBarHost(snackbarHostState) }
    ) { innerPaddings ->
        val isRefreshing = anime.loadState.mediator?.refresh is LoadState.Loading ||
                anime.loadState.refresh is LoadState.Loading

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                anime.refresh()
            },
            modifier = Modifier.fillMaxSize()
        ) {
            CatalogContent(content = anime, contentPadding = innerPaddings)
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
    val loadState = content.loadState
    val isMediatorLoading = loadState.mediator?.refresh is LoadState.Loading
    val isSourceLoading = loadState.source.refresh is LoadState.Loading
    val isInitialLoading = (isMediatorLoading || isSourceLoading) && content.itemCount == 0
    val contentIsEmpty = !isMediatorLoading && !isSourceLoading &&
            content.itemCount == 0 && loadState.mediator?.refresh != null

    when (sizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> ListContent(
            content = content,
            contentPadding = contentPadding,
            loadState = loadState,
            isInitialLoading = isInitialLoading,
            contentIsEmpty = contentIsEmpty
        )
        else -> GridContent(
            content = content,
            contentPadding = contentPadding,
            loadState = loadState,
            isInitialLoading = isInitialLoading,
            contentIsEmpty = contentIsEmpty
        )
    }
}

@OptIn(ExperimentalAniFluxUi::class)
@Composable
private fun ListContent(
    content: LazyPagingItems<Media>,
    contentPadding: PaddingValues = PaddingValues(),
    loadState: CombinedLoadStates = content.loadState,
    isInitialLoading: Boolean = false,
    contentIsEmpty: Boolean = false
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AniFluxTheme.paddings.medium),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(AniFluxTheme.paddings.small)
    ) {
        item { Spacer(modifier = Modifier.padding(top = AniFluxTheme.paddings.small)) }
        when {
            isInitialLoading -> {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        LoadingIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
            contentIsEmpty -> {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text("Nothing to show")
                    }
                }
            }
            else -> {
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

        if (loadState.mediator?.append is LoadState.Loading) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    LoadingIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@OptIn(ExperimentalAniFluxUi::class)
@Composable
private fun GridContent(
    content: LazyPagingItems<Media>,
    contentPadding: PaddingValues = PaddingValues(),
    loadState: CombinedLoadStates = content.loadState,
    isInitialLoading: Boolean = false,
    contentIsEmpty: Boolean = false
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(PreviewDefaults.PosterWidth),
        contentPadding = contentPadding,
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

        if (loadState.mediator?.append is LoadState.Loading) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    LoadingIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}