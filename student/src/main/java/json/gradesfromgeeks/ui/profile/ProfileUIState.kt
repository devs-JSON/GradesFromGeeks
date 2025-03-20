package json.gradesfromgeeks.ui.profile

import json.gradesfromgeeks.data.entity.Language

data class ProfileUIState(

    val profileUrl: String? = "",
    val name: String = "",

    val showBottomSheetTheme: Boolean = false,
    val isDarkTheme: Boolean = false,

    val showBottomSheetLanguage: Boolean = false,
    val languages: List<Language> = Language.entries,
    val currentLanguage: Language = Language.ENGLISH,

    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
)

