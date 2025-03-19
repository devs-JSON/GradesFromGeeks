package json.gradesfromgeeks.navigation.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import json.gradesfromgeeks.ui.home.navigation.homeScreen
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.navigation.profileScreen
import json.gradesfromgeeks.navigation.searchScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToRoot: (Screen) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        homeScreen(onNavigateToRoot)
        searchScreen(onNavigateToRoot)
        profileScreen(onNavigateToRoot)
    }
}
