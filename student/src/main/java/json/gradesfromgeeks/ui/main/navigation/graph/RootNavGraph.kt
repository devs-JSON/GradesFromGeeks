package json.gradesfromgeeks.ui.main.navigation.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import json.gradesfromgeeks.ui.main.navigation.Screen
import json.gradesfromgeeks.ui.main.navigation.chatBotScreen
import json.gradesfromgeeks.ui.main.navigation.ext.navigateTo
import json.gradesfromgeeks.ui.main.navigation.mainNavGraph
import json.gradesfromgeeks.ui.main.navigation.profileScreen
import json.gradesfromgeeks.ui.main.navigation.reviewNavGraph
import json.gradesfromgeeks.ui.main.navigation.searchScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
        mainNavGraph(onNavigateToRoot = navController::navigateTo)
        chatBotScreen(onNavigateBack = navController::navigateUp)
        reviewNavGraph(
            onNavigateBack = navController::navigateUp,
            onNavigateTo = navController::navigateTo
        )
        searchScreen(onNavigateTo = navController::navigateTo)
        profileScreen(onNavigateTo = navController::navigateTo)
    }
}
