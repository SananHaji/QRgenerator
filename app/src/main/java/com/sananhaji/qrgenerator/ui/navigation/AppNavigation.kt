package com.sananhaji.qrgenerator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sananhaji.qrgenerator.analytics.AppAnalytics
import com.sananhaji.qrgenerator.analytics.FirebaseAppAnalytics
import com.sananhaji.qrgenerator.ui.editor.QrEditorScreen
import com.sananhaji.qrgenerator.ui.home.HomeScreen
import com.sananhaji.qrgenerator.ui.template.TemplateBatchScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val analytics: AppAnalytics = remember(context) { FirebaseAppAnalytics(context) }

    // Compose screens live in one activity, so Analytics' automatic screen tracking sees only that;
    // the route pattern (without argument values) names the screen instead.
    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            destination.route?.let(analytics::logScreen)
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onSimple = { navController.navigate(Routes.editor(customize = false)) },
                onCustom = { navController.navigate(Routes.editor(customize = true)) },
                onTemplate = { navController.navigate(Routes.template(it.id)) },
            )
        }
        composable(
            route = Routes.EDITOR,
            arguments = listOf(navArgument(Routes.ARG_CUSTOMIZE) { type = NavType.BoolType }),
        ) { entry ->
            QrEditorScreen(
                startCustomizing = entry.arguments?.getBoolean(Routes.ARG_CUSTOMIZE) == true,
                onBack = navController::popBackStack,
            )
        }
        composable(
            route = Routes.TEMPLATE,
            arguments = listOf(navArgument(Routes.ARG_TEMPLATE_ID) { type = NavType.StringType }),
        ) {
            TemplateBatchScreen(onBack = navController::popBackStack)
        }
    }
}
