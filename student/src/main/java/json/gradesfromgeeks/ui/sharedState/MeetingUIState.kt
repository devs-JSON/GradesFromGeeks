package json.gradesfromgeeks.ui.sharedState

import json.gradesfromgeeks.data.entity.Meeting


data class MeetingUIState(
    val id: String = "",
    val mentor: MentorDetailsUIState = MentorDetailsUIState(),
    val time: Long = 0L,
    val subject: String = "",
    val notes: String = "",
    val isBook: Boolean = false,
    val price: String = ""
)

fun List<Meeting>.toMeetingListUIState() = map { it.toUIState() }

fun Meeting.toUIState(): MeetingUIState {
    return MeetingUIState(
        id = id,
        mentor = mentor.toUIState(),
        subject = subject,
        time = time,
        notes = notes,
        isBook = isBook,
        price = price
    )
}