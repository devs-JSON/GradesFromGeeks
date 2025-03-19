package json.gradesfromgeeks.ui.home.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.ui.home.HomeScreen
import json.gradesfromgeeks.ui.home.HomeUIEffect
import json.gradesfromgeeks.ui.main.navigation.Screen


fun NavGraphBuilder.homeScreen(onNavigateTo: (Screen) -> Unit) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            navigateTo = { navigate ->
                when (navigate) {
                    HomeUIEffect.NavigateToChatBooks -> Screen.ChatBot.withClearBackStack()
                        .also(onNavigateTo)

                    is HomeUIEffect.NavigateToMentorProfile -> {

                    }

                    is HomeUIEffect.NavigateToSeeAll -> {
                        Screen.SeeAll.args = bundleOf(Pair("type", navigate.type.value))
                        Screen.SeeAll.withClearBackStack().also(onNavigateTo)
                    }

                    is HomeUIEffect.NavigateToUniversityProfile -> {

                    }

                    is HomeUIEffect.NavigateToSubject -> {

                    }

                    HomeUIEffect.NavigateToNotification -> {}

                    else -> {}
                }
            },
        )
    }
}
