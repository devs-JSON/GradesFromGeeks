package json.gradesfromgeeks.ui.home.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.home.HomeScreen
import json.gradesfromgeeks.ui.home.HomeUIEffect


fun NavGraphBuilder.homeScreen(onNavigateTo: (Screen) -> Unit) {
    composable(route = Screen.Home.route) {
        HomeScreen(
            navigateTo = { navigate ->
                when (navigate) {
                    HomeUIEffect.NavigateToChatBooks -> Screen.ChatBot.withClearBackStack()
                        .also(onNavigateTo)

                    is HomeUIEffect.NavigateToMentorProfile -> {
                        Screen.Mentor.args = bundleOf(Pair("id", navigate.id))
                        Screen.Mentor.withClearBackStack().also(onNavigateTo)
                    }

                    is HomeUIEffect.NavigateToSeeAll -> {
                        Screen.SeeAll.args = bundleOf(Pair("type", navigate.type.value))
                        Screen.SeeAll.withClearBackStack().also(onNavigateTo)
                    }

                    is HomeUIEffect.NavigateToUniversityProfile -> {
                        Screen.University.args = bundleOf(Pair("id", navigate.id))
                        Screen.University.withClearBackStack()
                            .also(onNavigateTo)
                    }

                    is HomeUIEffect.NavigateToSubject -> {
                        Screen.Subject.args = bundleOf(Pair("id", navigate.id))
                        Screen.Subject.withClearBackStack()
                            .also(onNavigateTo)
                    }

                    HomeUIEffect.NavigateToNotification -> {
                        Screen.Notification.withClearBackStack()
                            .also(onNavigateTo)
                    }

                    else -> {}
                }
            },
        )
    }
}
