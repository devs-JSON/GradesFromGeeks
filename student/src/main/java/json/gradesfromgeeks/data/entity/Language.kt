package json.gradesfromgeeks.data.entity

import androidx.compose.ui.unit.LayoutDirection

enum class Language(
    val value: String, val abbreviation: String, val layoutDirection: LayoutDirection
) {
    ENGLISH(value = "English", "en", layoutDirection = LayoutDirection.Ltr),
    ARABIC(value = "عربي", "ar", layoutDirection = LayoutDirection.Rtl)
}

fun String?.getLanguage(): Language {
    Language.entries.forEach {
        if (it.abbreviation == this) {
            return it
        }
    }
    return Language.ENGLISH
}