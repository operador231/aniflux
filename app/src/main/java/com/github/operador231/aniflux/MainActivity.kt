package com.github.operador231.aniflux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.operador231.core.navigation.FeatureApi
import com.github.operador231.core.navigation.NavigationPriority
import com.github.operador231.core.navigation.Route
import com.github.operador231.core.ui.screen.StubScreen
import com.github.operador231.core.ui.theme.AniFluxTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class MainActivity : ComponentActivity() {
    @Inject
    @JvmSuppressWildcards
    public lateinit var featureApis: Set<FeatureApi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateNavigation(featureApis)

        enableEdgeToEdge()
        setContent {
            AniFluxTheme {
                AniFluxApp(featureApis)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@PreviewScreenSizes
@Composable
private fun AniFluxApp(
    featureApis: Set<FeatureApi> = emptySet()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val globalNavDestinations = featureApis.mapNotNull { it.navInfo }
    val navSuiteType = if (globalNavDestinations.isEmpty()) NavigationSuiteType.None else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
            adaptiveInfo = currentWindowAdaptiveInfo()
        )
    }
    val navItems = globalNavDestinations
        .filter { it.priority != NavigationPriority.UNSPECIFIED }
        .sortedBy { it.order}

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            navItems.forEach { item ->

                val isSelected =
                    navBackStackEntry?.destination?.hasRoute(item.route::class) ?: false

                item(
                    selected = isSelected,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true //isSelected
                        }
                    },
                    icon = {
                        Icon(
                            imageVector =
                                ImageVector.vectorResource(if (isSelected) item.iconFillResId else item.iconResId),
                            contentDescription = stringResource(item.labelResId)
                        )
                    },
                    label = { Text(stringResource(item.labelResId)) }
                )
            }
        },
        layoutType = navSuiteType
    ) {
        AniFluxNavHost(
            featureApis = featureApis,
            navController = navController,
            startRoute = navItems.firstOrNull()?.route
        )
    }
}

@Composable
private fun AniFluxNavHost(
    featureApis: Set<FeatureApi>,
    navController: NavHostController,
    startRoute: Route?
) {
    NavHost(
        navController = navController,
        startDestination = startRoute ?: Route.Stub
    ) {
        composable<Route.Stub> {
            StubScreen()
        }

        featureApis.forEach { api ->
            api.registerGraph(
                navGraphBuilder = this,
                navController = navController
            )
        }
    }
}

private fun validateNavigation(features: Set<FeatureApi>) {
    val itemsWithSource = features.mapNotNull { api ->
        api.navInfo?.let { info -> info to api::class.qualifiedName }
    }

    itemsWithSource.groupBy { it.first.priority }
        .forEach { (priority, pairs) ->
            val duplicateOrders = pairs.groupBy { it.first.order }
                .filterValues { it.size > 1 }

            if (duplicateOrders.isNotEmpty()) {
                val conflictDetails = duplicateOrders.entries.joinToString(separator = "\n") { (order, list) ->
                    val sources = list.joinToString { (info, className) ->
                        "${info.route::class.simpleName} (provided by $className)"
                    }
                    "  • Order $order: $sources"
                }

                throw IllegalStateException(
                    """
                    Navigation Collision in $priority!
                    $conflictDetails
                    Please ensure each feature in the $priority group has a unique 'order' value.
                    """.trimIndent()
                )
            }
        }
}
