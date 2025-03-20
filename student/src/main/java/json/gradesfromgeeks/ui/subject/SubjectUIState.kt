package json.gradesfromgeeks.ui.subject

import json.gradesfromgeeks.data.entity.Subject
import json.gradesfromgeeks.ui.university.ContentCountUIState
import json.gradesfromgeeks.ui.home.SubjectDetailsUiState
import json.gradesfromgeeks.ui.sharedState.MentorUiState

data class SubjectUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val contentCount: ContentCountUIState = ContentCountUIState(),
    val subjectDetails: SubjectDetailsUiState = SubjectDetailsUiState(),
    val subjectMentors: List<MentorUiState> = emptyList()
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

