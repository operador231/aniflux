package com.github.operador231.feature.catalog.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.github.operador231.core.navigation.FeatureApi
import com.github.operador231.core.navigation.FeatureKey
import com.github.operador231.core.navigation.FeatureNavInfo
import com.github.operador231.core.navigation.NavigationPriority
import com.github.operador231.feature.catalog.api.CatalogRoute
import com.github.operador231.feature.catalog.impl.ui.screen.CatalogScreen
import javax.inject.Inject

@FeatureKey(FeatureCatalog::class)
public class FeatureCatalog @Inject constructor() : FeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<CatalogRoute> {
            CatalogScreen()
        }
    }

    override val navInfo: FeatureNavInfo = FeatureNavInfo(
        route = CatalogRoute,
        labelResId = R.string.st_catalog,
        iconResId = R.drawable.ic_video_library,
        iconFillResId = R.drawable.ic_video_library_fill,
        priority = NavigationPriority.PRIMARY,
        order = 1,
    )
}