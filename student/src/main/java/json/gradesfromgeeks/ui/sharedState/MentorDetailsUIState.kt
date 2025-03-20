package json.gradesfromgeeks.ui.sharedState

import json.gradesfromgeeks.data.entity.Mentor
import json.gradesfromgeeks.ui.home.SubjectDetailsUiState
import json.gradesfromgeeks.ui.home.toSubjectUiState

data class MentorDetailsUIState(
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val rate: Double = 0.0,
    val numberReviewers: Int = 0,
    val summaries: Int = 0,
    val videos: Int = 0,
    val meeting: Int = 0,
    val subjects: List<SubjectDetailsUiState> = emptyList(),
    val university: String = ""
)


fun Mentor.toUIState(): MentorDetailsUIState {
    return MentorDetailsUIState(
        id = id,
        imageUrl = imageUrl,
        name = name,
        rate = rate,
        numberReviewers = numberReviewers,
        summaries = summaries,
        meeting = meeting,
        subjects = subjects.toSubjectUiState(),
        university = university,
        videos = videos
    )
}
