package json.gradesfromgeeks.ui.main.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


fun NavGraphBuilder.testRout(
    onNavigateTo: (Screen) -> Unit,
    onNavigateBack: () -> Unit
) {
    composable(
        route = Screen.Test.route
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        }
    }
}
