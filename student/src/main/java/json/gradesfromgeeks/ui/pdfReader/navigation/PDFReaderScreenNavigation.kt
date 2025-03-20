package json.gradesfromgeeks.ui.pdfReader.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import json.gradesfromgeeks.navigation.Screen
import json.gradesfromgeeks.ui.pdfReader.PDFViewerScreen

fun NavGraphBuilder.pdvReaderNavGraph(onNavigateBack: () -> Unit) {
    composable(
        route = Screen.PDFReader.route
    ) {
        PDFViewerScreen(
            navigateBack = onNavigateBack
        )
    }
}