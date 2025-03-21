package json.gradesfromgeeks.navigation

import android.os.Bundle
import json.gradesfromgeeks.R

const val navigationRouteMain = "main"
const val navigationRouteHome = "home"

const val navigationRouteChatBot = "chatBot"
const val navigationRouteSeeAll = "seeAll"
const val navigationRouteReview = "review"
const val navigationRouteSearch = "search"
const val navigationRouteProfile = "profile"
const val navigationRouteDownloads = "downloads"

const val navigationRouteMentor = "mentor"
const val navigationRouteUniversity = "university"
const val navigationRouteSubject = "Subject"

const val navigationRouteIndividualMeeting = "individualMeeting"
const val navigationRouteNotification = "notification"
const val navigationRoutePDFReader = "pDFReader"


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


    data object Main : Screen(navigationRouteMain)

    data object Home : Screen(
        route = navigationRouteHome,
        selectedIcon = R.drawable.ic_home_selected,
        unselectedIcon = R.drawable.ic_home_unselected
    )

    data object Downloads : Screen(
        route = navigationRouteDownloads,
        selectedIcon = R.drawable.ic_download_selected,
        unselectedIcon = R.drawable.ic_download_unselected
    )

    data object ChatBot : Screen(navigationRouteChatBot)

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

    data object SeeAll : Screen(navigationRouteSeeAll)
    data object Mentor : Screen(route = navigationRouteMentor)
    data object University : Screen(route = navigationRouteUniversity)
    data object Subject : Screen(route = navigationRouteSubject)

    data object IndividualMeeting : Screen(navigationRouteIndividualMeeting)
    data object Review: Screen(navigationRouteReview)

    data object Notification : Screen(navigationRouteNotification)
    data object PDFReader : Screen(navigationRoutePDFReader)

}
