package json.gradesfromgeeks.ui.notification

import json.gradesfromgeeks.data.entity.Notification
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import kotlinx.coroutines.flow.update


class NotificationsViewModel (
    private val mindfulMentorRepository: GradesFromGeeksRepository
) : BaseViewModel<NotificationsUIState, NotificationUIEffect>(NotificationsUIState()) {

    init {
        getNotificationDetails()
    }

    private fun getNotificationDetails() {
        _state.update { it.copy(isLoading = true, error = "") }
        tryToExecute(
                { mindfulMentorRepository.getNotifications() },
                ::onSuccess,
                ::onError
        )
    }

    private fun onError() {

        _state.update {
            it.copy(
                    isLoading = false,
                    error = if (_state.value.notifications.isEmpty()) "connection error" else ""
            )
        }
    }

    private fun onSuccess(notifications: List<Notification>) {
        _state.update {
            it.copy(
                    notifications = (it.notifications + notifications.toUIState()),
                    isLoading = false,
            )
        }
    }

}