package json.gradesfromgeeks.ui.main.navigation

import android.os.Bundle


const val navigationRouteChatBot = "chatBot"
const val navigationRouteReview = "review"


sealed class Screen(
    val route: String,
    var routePath: String? = null,
    var clearBackStack: Boolean = false,
    val restoreState: Boolean = true,
    val selectedIcon: Int? = null,
    val unselectedIcon: Int? = null,
    var args: Bundle? = null
) {
    fun withClearBackStack() = apply { clearBackStack = true }

    fun routeWith(path: String) = apply {
        routePath = path
    }

    data object ChatBot : Screen(navigationRouteChatBot)
    data object Review: Screen(navigationRouteReview)



}
