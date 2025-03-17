package json.gradesfromgeeks.ui.sharedState

import json.gradesfromgeeks.data.entity.Mentor

data class MentorUiState(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val rate: Double = 0.0,
    val numberReviewers: Int = 0
)

fun Mentor.toUiState() = MentorUiState(
    id = id,
    name = name,
    imageUrl = imageUrl,
    rate = rate,
    numberReviewers = numberReviewers,
)


fun List<Mentor>.toUiState() = map { it.toUiState() } 