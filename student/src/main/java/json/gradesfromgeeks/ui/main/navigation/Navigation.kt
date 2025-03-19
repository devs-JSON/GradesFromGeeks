package json.gradesfromgeeks.ui.main.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import json.gradesfromgeeks.ui.chat.ChatBotScreen
import json.gradesfromgeeks.ui.main.screen.MainScreen
import json.gradesfromgeeks.ui.main.navigation.ext.navigateTo
import json.gradesfromgeeks.ui.main.navigation.graph.MainNavGraph
import json.gradesfromgeeks.ui.profile.ProfileScreen
import json.gradesfromgeeks.ui.review.ReviewScreen
import json.gradesfromgeeks.ui.search.SearchScreen
import json.gradesfromgeeks.ui.search.SearchUIEffect


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun NavGraphBuilder.mainNavGraph(onNavigateToRoot: (Screen) -> Unit) {
    composable(route = Screen.Main.route) {

        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val bottomBar: @Composable () -> Unit = {
            HRBottomNavigation(
                screens = listOf(
                    Screen.Home,
                    Screen.Search,
                    Screen.Downloads,
                    Screen.Profile,
                ),
                onNavigateTo = navController::navigateTo,
                currentDestination = navBackStackEntry?.destination
            )
        }

        val nestedNavGraph: @Composable () -> Unit = {
            MainNavGraph(
                navController = navController,
                onNavigateToRoot = onNavigateToRoot
            )
        }

        MainScreen(
            bottomBar = bottomBar,
            nestedNavGraph = nestedNavGraph
        )
    }

}



fun NavGraphBuilder.chatBotScreen(onNavigateBack: () -> Unit) {
    composable(
        route = Screen.ChatBot.route
    ) {
        ChatBotScreen(onNavigateBack = onNavigateBack)
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun NavGraphBuilder.profileScreen(onNavigateTo: (Screen) -> Unit) {
    composable(
        route = Screen.Profile.route
    ) {
        ProfileScreen(
            navigateTo = { navigate ->
                when (navigate) {
                    else -> {}
                }
            }
        )
    }
}

fun NavGraphBuilder.reviewNavGraph(onNavigateBack: () -> Unit, onNavigateTo: (Screen) -> Unit) {
    composable(
        route = Screen.Review.route
    ) {
        ReviewScreen(
            navigateBack = onNavigateBack,
        )
    }
}


fun NavGraphBuilder.searchScreen(onNavigateTo: (Screen) -> Unit) {
    composable(
        route = Screen.Search.route
    ) {
        SearchScreen(
            navigateTo = { navigate ->
                when (navigate) {
                    is SearchUIEffect.NavigateToSeeAll -> {

                    }

                    is SearchUIEffect.NavigateToMentorProfile -> {

                    }

                    is SearchUIEffect.NavigateToUniversityProfile -> {

                    }

                    is SearchUIEffect.NavigateToSubject -> {

                    }

                    else -> {}
                }
            },
        )
    }
}