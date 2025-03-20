package json.gradesfromgeeks.ui.seeAll

sealed interface SeeAllUIEffect {
    data object SeeAllError : SeeAllUIEffect
}
