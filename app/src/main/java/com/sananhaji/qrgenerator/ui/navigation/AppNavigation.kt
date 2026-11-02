package com.sananhaji.qrgenerator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sananhaji.qrgenerator.ui.editor.QrEditorScreen
import com.sananhaji.qrgenerator.ui.home.HomeScreen
import com.sananhaji.qrgenerator.ui.template.TemplateBatchScreen

@Composable
fun AppNavigation(onSendLogs: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onSimple = { navController.navigate(Routes.editor(customize = false)) },
                onCustom = { navController.navigate(Routes.editor(customize = true)) },
                onTemplate = { navController.navigate(Routes.template(it.id)) },
                onSendLogs = onSendLogs,
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
