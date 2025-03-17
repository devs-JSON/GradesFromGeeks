package json.gradesfromgeeks.ui.main.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.ui.chat.ChatBotScreen
import json.gradesfromgeeks.ui.review.ReviewScreen


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