package com.flynn.feature_home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.flynn.domain.navigation.Home
import com.flynn.feature_home.HomeScreen

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable<Home>{
        HomeScreen()
    }
}