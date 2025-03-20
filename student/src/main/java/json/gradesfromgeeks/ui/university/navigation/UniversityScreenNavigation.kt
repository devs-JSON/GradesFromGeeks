package json.gradesfromgeeks.ui.university.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.seeAll.SeeAllType
import json.gradesfromgeeks.ui.university.UniversityScreen
import json.gradesfromgeeks.ui.university.UniversityUIEffect

fun NavGraphBuilder.universityNavGraph(
    onNavigateTo: (Screen) -> Unit,
    onNavigateBack: () -> Unit
) {
    composable(
        route = Screen.University.route
    ) {

        val value = Screen.University.args?.getString("id").toString()
        UniversityScreen(
            id = value,
            onNavigateTo = {
                when (it) {
                    is UniversityUIEffect.NavigateToMentorProfile -> {
                        Screen.Mentor.args = bundleOf(Pair("id", it.id))
                        Screen.Mentor.withClearBackStack()
                            .also(onNavigateTo)
                    }

                    is UniversityUIEffect.NavigateToSeeAll -> {
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