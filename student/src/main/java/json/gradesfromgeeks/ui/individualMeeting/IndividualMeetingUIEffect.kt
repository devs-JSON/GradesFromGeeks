package json.gradesfromgeeks.ui.individualMeeting

sealed interface IndividualMeetingUIEffect {
    data object IndividualMeetingError : IndividualMeetingUIEffect
}
