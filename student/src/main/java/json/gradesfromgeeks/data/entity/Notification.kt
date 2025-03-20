package json.gradesfromgeeks.data.entity

import json.gradesfromgeeks.ui.notification.NotificationType


data class Notification(
    val id: Int,
    val ownerId: Int,
    val subjectId: Int,
    val timeCreated: String,
    val type: NotificationType,
    val viewed: Boolean = false,
    val mentorName: String,
)
