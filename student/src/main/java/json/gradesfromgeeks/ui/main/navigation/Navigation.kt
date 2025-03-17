package json.gradesfromgeeks.ui.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.ui.chat.ChatBotScreen
import json.gradesfromgeeks.ui.review.ReviewScreen
import json.gradesfromgeeks.ui.search.SearchScreen
import json.gradesfromgeeks.ui.search.SearchUIEffect


fun NavGraphBuilder.chatBotScreen(onNavigateBack: () -> Unit) {
    composable(
        route = Screen.ChatBot.route
    ) {
        ChatBotScreen(onNavigateBack = onNavigateBack)
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