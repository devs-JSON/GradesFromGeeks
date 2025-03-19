package json.gradesfromgeeks.ui.seeAll.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.seeAll.SeeAllScreen
import json.gradesfromgeeks.ui.seeAll.SeeAllType
import json.gradesfromgeeks.ui.seeAll.toSeeAllType

fun NavGraphBuilder.onSeeAllScreen(onNavigateTo: (Screen) -> Unit, onNavigateBack: () -> Unit) {
    this.composable(
        route = Screen.SeeAll.route
    ) {
        val value = Screen.SeeAll.args?.getString("type").toString().toSeeAllType()

        SeeAllScreen(
            type = value,
            navigateTo = { id ->
                when (value) {
                    SeeAllType.Mentors -> {
                        Screen.Mentor.args = bundleOf(Pair("id", id))
                        Screen.Mentor.withClearBackStack().also(onNavigateTo)
                    }

                    SeeAllType.Universities -> {
                        Screen.University.args = bundleOf(Pair("id", id))
                        Screen.University.withClearBackStack()
                            .also(onNavigateTo)
                    }

                    SeeAllType.Subjects -> {
                        Screen.Subject.args = bundleOf(Pair("id", id))
                        Screen.Subject.withClearBackStack()
                            .also(onNavigateTo)
                    }
                }
            },
            navigateBack = onNavigateBack
        )
    }
}
