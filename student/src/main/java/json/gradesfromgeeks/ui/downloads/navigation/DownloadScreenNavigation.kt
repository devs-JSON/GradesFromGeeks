package json.gradesfromgeeks.ui.downloads.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.downloads.DownloadsScreen
import json.gradesfromgeeks.ui.downloads.DownloadsUIEffect

fun NavGraphBuilder.downloadsScreen(onNavigateTo: (Screen) -> Unit) {
    composable(
        route = Screen.Downloads.route
    ) {
        DownloadsScreen(
            onNavigateTo = { navigateTo ->
                when (navigateTo) {
                    DownloadsUIEffect.NavigateToPDFReader -> Screen.PDFReader.withClearBackStack()
                        .also(onNavigateTo)

                    DownloadsUIEffect.NavigateToReviewScreen -> Screen.Review.withClearBackStack()
                        .also(onNavigateTo)

                    else -> {}
                }
            }
        )
    }
}