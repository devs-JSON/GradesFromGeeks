package json.gradesfromgeeks.navigation.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.navigation.chatBotScreen
import json.gradesfromgeeks.navigation.ext.navigateTo
import json.gradesfromgeeks.navigation.mainNavGraph
import json.gradesfromgeeks.navigation.profileScreen
import json.gradesfromgeeks.navigation.reviewNavGraph
import json.gradesfromgeeks.navigation.searchScreen
import json.gradesfromgeeks.ui.individualMeeting.navigation.individualMeetingNavGraph
import json.gradesfromgeeks.ui.mentor.navigation.mentorNavGraph
import json.gradesfromgeeks.ui.seeAll.navigation.onSeeAllScreen

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
        searchScreen(onNavigateTo = navController::navigateTo)
        profileScreen(onNavigateTo = navController::navigateTo)
        chatBotScreen(onNavigateBack = navController::navigateUp)
        onSeeAllScreen(
            onNavigateTo = navController::navigateTo,
            onNavigateBack = navController::navigateUp
        )
        mentorNavGraph(
            onNavigateToRoot = navController::navigateTo,
            onNavigateBack = navController::navigateUp
        )
        individualMeetingNavGraph(
            onNavigateBack = navController::navigateUp
        )
        reviewNavGraph(
            onNavigateBack = navController::navigateUp,
            onNavigateTo = navController::navigateTo
        )

    }
}
