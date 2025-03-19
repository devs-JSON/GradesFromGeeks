package json.gradesfromgeeks.ui.main.navigation

import android.os.Bundle
import json.gradesfromgeeks.R


const val navigationRouteChatBot = "chatBot"
const val navigationRouteReview = "review"
const val navigationRouteSearch = "search"
const val navigationRouteProfile = "profile"


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

    data object Search : Screen(
        route = navigationRouteSearch,
        selectedIcon = R.drawable.ic_search_selected,
        unselectedIcon = R.drawable.ic_search_unselected
    )

    data object Profile : Screen(
        route = navigationRouteProfile,
        selectedIcon = R.drawable.ic_profile_selected,
        unselectedIcon = R.drawable.ic_profile_unselected
    )

}
