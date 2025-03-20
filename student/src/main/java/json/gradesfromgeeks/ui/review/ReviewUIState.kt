package json.gradesfromgeeks.ui.review

data class ReviewUIState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val isVideoFullScreen: Boolean = false,
    val summaries: String = "",
)
