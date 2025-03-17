package json.gradesfromgeeks.ui.profile

sealed interface ProfileUIEffect {
    data object ProfileError : ProfileUIEffect
    data object NavigateToSignIn : ProfileUIEffect

}