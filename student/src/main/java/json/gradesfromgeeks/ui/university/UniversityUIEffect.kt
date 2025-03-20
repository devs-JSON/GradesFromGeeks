package json.gradesfromgeeks.ui.university


sealed interface UniversityUIEffect {
    data object UniversityError : UniversityUIEffect

    data object NavigateToSeeAll : UniversityUIEffect

    data class NavigateToMentorProfile(val id : String) : UniversityUIEffect
}