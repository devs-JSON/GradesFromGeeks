package json.gradesfromgeeks.data.entity

data class HuggingFaceRequest(
    val inputs: InputData
)

data class InputData(
    val question: String,
    val context: String
)

data class HuggingFaceResponse(
    val answer: String?,
    val score: Float?,
    val start: Int?,
    val end: Int?
)
