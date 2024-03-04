package com.sananhaji.qrgenerator.screens

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

object NavVariables {
    val home = "home"
    val single = "single"
    val multi = "multi"
    val withLogo = "withLogo"
    val withLogoMulti = "withLogoMulti"
}


@Composable
fun Navigation(activity: Activity, navController: NavHostController) {

    NavHost(navController = navController, startDestination = NavVariables.home) {
        composable(NavVariables.home) { Home(activity, navController) }
        composable(NavVariables.single) { SingleQrGenerator(navController) }
        composable(NavVariables.multi) { MultiQrGenerator() }
        composable(NavVariables.withLogo) { WithLogoQrGenerator(navController) }
        composable(NavVariables.withLogoMulti) { MultiWithLogoQrGenerator(navController) }
    }
}