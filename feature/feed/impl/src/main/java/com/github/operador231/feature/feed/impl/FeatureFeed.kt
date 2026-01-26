package com.github.operador231.feature.feed.impl

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.github.operador231.core.navigation.FeatureApi
import com.github.operador231.core.navigation.FeatureKey
import com.github.operador231.core.navigation.FeatureNavInfo
import com.github.operador231.core.navigation.NavigationPriority
import com.github.operador231.core.ui.screen.StubScreen
import com.github.operador231.feature.feed.api.HomeRoute
import javax.inject.Inject

@FeatureKey(FeatureFeed::class)
public class FeatureFeed @Inject constructor() : FeatureApi {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<HomeRoute> {
            StubScreen(text = stringResource(R.string.st_feat_in_dev))
        }
    }

    override val navInfo: FeatureNavInfo = FeatureNavInfo(
        route = HomeRoute,
        labelResId = R.string.st_home,
        iconResId = R.drawable.ic_home,
        iconFillResId = R.drawable.ic_home_fill,
        priority = NavigationPriority.PRIMARY,
        order = 0
    )
}