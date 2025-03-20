package json.gradesfromgeeks.ui.sharedState

enum class SeeAllType(val value: String) {
    Mentors("Mentors"),
    Universities("Universities"),
    Subjects("Subjects"),

}

fun String.toSeeAllType(): SeeAllType {
    return when (this) {
        SeeAllType.Mentors.value -> SeeAllType.Mentors
        SeeAllType.Universities.value -> SeeAllType.Universities
        else -> SeeAllType.Subjects
    }
}
