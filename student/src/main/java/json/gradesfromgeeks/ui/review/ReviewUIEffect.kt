package json.gradesfromgeeks.ui.review


sealed interface ReviewUIEffect {

    data object ReviewError : ReviewUIEffect

}