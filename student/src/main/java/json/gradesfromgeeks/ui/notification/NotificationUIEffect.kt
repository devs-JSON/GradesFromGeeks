package json.gradesfromgeeks.ui.notification

sealed interface NotificationUIEffect {
    data object NotificationError : NotificationUIEffect

    data class NavigateToScreens(val type: NotificationType) : NotificationUIEffect

}
