package json.gradesfromgeeks.ui.sharedState

import json.gradesfromgeeks.data.entity.Summaries


data class SummeryDetailsUIState(
    val chapterNumber: String = "",
    val chapterDescription: String = "",
    val piedPrice: String = "",
    val isBuy: Boolean = false
)



fun List<Summaries>.toListUIState() = map { it.toUIState() }

fun Summaries.toUIState(): SummeryDetailsUIState {
    return SummeryDetailsUIState(
        chapterNumber = chapterNumber,
        chapterDescription = chapterDescription,
        piedPrice = piedPrice,
        isBuy = isBuy
    )
}
