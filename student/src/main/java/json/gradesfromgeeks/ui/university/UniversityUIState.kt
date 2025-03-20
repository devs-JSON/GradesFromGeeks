package json.gradesfromgeeks.ui.university

import json.gradesfromgeeks.data.entity.University
import json.gradesfromgeeks.ui.home.SubjectDetailsUiState
import json.gradesfromgeeks.ui.home.toSubjectUiState
import json.gradesfromgeeks.ui.sharedState.MentorUiState
import json.gradesfromgeeks.ui.sharedState.toUiState


data class UniversityUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val universityDetails: UniversityDetailsUIState = UniversityDetailsUIState()

)


data class UniversityDetailsUIState(
    val universityName: String = "",
    val universityImageUrl: String = "",
    val universityDescription: String = "",
    val mentorNumber: String = "",
    val summaryNumber: String = "",
    val videoNumber: String = "",
    val subjectList: List<SubjectDetailsUiState> = emptyList(),
    val mentorList: List<MentorUiState> = emptyList()
)

data class ContentCountUIState(
    val count: String = "",
    val contentName: String = "",
)

fun University.toUIState(): UniversityDetailsUIState {
    return UniversityDetailsUIState(
        universityName = name,
        universityImageUrl = imageUrl,
        universityDescription = address,
        mentorNumber = mentorNumber,
        summaryNumber = summaryNumber,
        videoNumber = videoNumber,
        subjectList = subjects.toSubjectUiState(),
        mentorList = mentors.toUiState()
    )
}
