package json.gradesfromgeeks.ui.mentor

import json.gradesfromgeeks.ui.sharedState.MeetingUIState
import json.gradesfromgeeks.ui.sharedState.MentorDetailsUIState
import json.gradesfromgeeks.ui.sharedState.SummeryDetailsUIState


data class MentorUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val mentorDetailsUIState: MentorDetailsUIState = MentorDetailsUIState(),
    val mentorSummariseList: List<SummeryDetailsUIState> = emptyList(),
    val mentorVideoList: List<SummeryDetailsUIState> = emptyList(),
    val mentorMeetingList: List<MeetingUIState> = emptyList()
)


