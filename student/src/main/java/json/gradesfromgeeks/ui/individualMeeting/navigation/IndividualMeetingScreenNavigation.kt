package json.gradesfromgeeks.ui.individualMeeting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.individualMeeting.IndividualMeetingScreen

fun NavGraphBuilder.individualMeetingNavGraph(
    onNavigateBack: () -> Unit
) {
    composable(
        route = Screen.IndividualMeeting.route
    ) {
        IndividualMeetingScreen(
            navigateTo = {},
            navigateBack = onNavigateBack
        )
    }
}
