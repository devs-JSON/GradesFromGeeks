package json.gradesfromgeeks.ui.subject.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.seeAll.SeeAllType
import json.gradesfromgeeks.ui.subject.SubjectScreen
import json.gradesfromgeeks.ui.subject.SubjectUIEffect

fun NavGraphBuilder.subjectNavGraph(
    onNavigateTo: (Screen) -> Unit,
    onNavigateBack: () -> Unit
) {
    composable(
        route = Screen.Subject.route
    ) {

        val value = Screen.Subject.args?.getString("id").toString()
        SubjectScreen(
            id = value,
            onNavigateTo = {
                when (it) {
                    is SubjectUIEffect.NavigateToMentorProfile -> {
                        Screen.Mentor.args = bundleOf(Pair("type", it.id))
                        Screen.Mentor.withClearBackStack()
                            .also(onNavigateTo)
                    }

                    is SubjectUIEffect.NavigateToSeeAll -> {
                        Screen.SeeAll.args = bundleOf(Pair("type", SeeAllType.Mentors.value))
                        Screen.SeeAll.withClearBackStack().also(onNavigateTo)
                    }

                    else -> {}
                }
            },
            navigateBack = onNavigateBack
        )
    }
}