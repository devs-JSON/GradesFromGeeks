package json.gradesfromgeeks.ui.main.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import json.gradesfromgeeks.ui.main.navigation.Screen
import json.gradesfromgeeks.ui.main.navigation.ext.navigateTo
import json.gradesfromgeeks.ui.main.navigation.testRout

@Composable
fun RootNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Screen
) {
    NavHost(
        navController = navController,
        route = "root_host",
        startDestination = startDestination.route,
        modifier = modifier,
    ) {
        testRout(
            onNavigateTo = { screen -> navController.navigateTo(screen) },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
