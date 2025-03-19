package json.gradesfromgeeks.ui.home

import json.gradesfromgeeks.data.entity.Subject
import json.gradesfromgeeks.ui.sharedState.UniversityUiState

data class HomeUIState(
    val mentors: List<MentorUiState> = emptyList(),
    val subjects: List<SubjectDetailsUiState> = emptyList(),
    val university: List<UniversityUiState> = emptyList(),
    val upComingMeetings: List<MeetingUiState> = emptyList(),

    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

data class MentorUiState(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val rate: Double = 0.0,
    val numberReviewers: Int = 0
)

data class SubjectDetailsUiState(
    val id: String = "",
    val name: String = "",
    val mentorNumber: String = "",
    val summaryNumber: String = "",
    val videoNumber: String = "",
    val mentors: List<String> = emptyList()
)

data class MeetingUiState(
    val id: String = "",
    val subject: String = "",
    val time: Long = 0L,
    val mentorName: String = "",
    val notes: String = "",
    val reminder: Long = 0L,
    val enableJoin: Boolean = false,
)


fun Subject.toSubjectUiState() = SubjectDetailsUiState(
    id = id,
    name = name,
    mentorNumber = mentorNumber,
    summaryNumber = summaryNumber,
    videoNumber = videoNumber,
    mentors = mentors
)

fun List<Subject>.toSubjectUiState() = map { it.toSubjectUiState() }