package json.gradesfromgeeks.ui.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.ui.home.HomeScreen
import json.gradesfromgeeks.ui.main.navigation.Screen


fun NavGraphBuilder.homeScreen(onNavigateTo: (Screen) -> Unit) {
    composable(route = Screen.Home.route) {
        HomeScreen()
    }
}
