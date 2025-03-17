package json.gradesfromgeeks.ui.sharedState

import json.gradesfromgeeks.data.entity.University

data class UniversityUiState(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val imageUrl: String = ""
)

fun List<Any>.showSeeAll(): Boolean {
    return this.size > 3
}

fun University.toUniversityUiState() = UniversityUiState(
    id = id,
    imageUrl = imageUrl,
    name = name,
    address = address
)

fun List<University>.toUniversityUiState() = map { it.toUniversityUiState() }
