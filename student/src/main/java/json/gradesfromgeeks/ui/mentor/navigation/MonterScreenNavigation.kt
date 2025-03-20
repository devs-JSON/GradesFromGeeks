package json.gradesfromgeeks.ui.mentor.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.mentor.MentorScreen
import json.gradesfromgeeks.ui.mentor.MentorUIEffect


fun NavGraphBuilder.mentorNavGraph(onNavigateToRoot: (Screen) -> Unit, onNavigateBack: () -> Unit) {
    composable(
        route = Screen.Mentor.route
    ) {

        val value = Screen.Mentor.args?.getString("id").toString()
        MentorScreen(
            id = value,
            onNavigateTo = {
                when (it) {
                    is MentorUIEffect.NavigateToScheduleMeeting -> {
                        Screen.IndividualMeeting.also(onNavigateToRoot)
                    }

                    else -> {}
                }
            },
            navigateBack = onNavigateBack
        )
    }
}
