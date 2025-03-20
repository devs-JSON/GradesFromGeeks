package json.gradesfromgeeks.ui.notification.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.notification.NotificationScreen

fun NavGraphBuilder.notificationNavGraph(
    onNavigateBack: () -> Unit
) {
    composable(
        route = Screen.Notification.route
    ) {
        NotificationScreen(
            onNavigateBack = onNavigateBack
        )
    }
}