package json.gradesfromgeeks.ui.profile

import androidx.lifecycle.viewModelScope
import json.gradesfromgeeks.data.entity.Language
import json.gradesfromgeeks.data.entity.User
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val gradesFromGeeksRepository: GradesFromGeeksRepository,
) : BaseViewModel<ProfileUIState, ProfileUIEffect>(ProfileUIState()) {

    init {
        getUserInfo()
        getLanguage()
        getTheme()
    }


    private fun getUserInfo() {
        tryToExecute(
            { gradesFromGeeksRepository.getUserData() },
            ::onSuccess,
            ::onError
        )

    }

    private fun onError() {
     updateState { it.copy(isSuccess = false) }
    }

    private fun onSuccess(studentInfo: User?) {
        updateState {
            it.copy(
                profileUrl = studentInfo?.profilePictureUrl,
                name = studentInfo?.username?:"",
            )
        }
    }

    //region Theme
    private fun getTheme() {
        viewModelScope.launch {
            gradesFromGeeksRepository.getTheme().distinctUntilChanged().collectLatest { theme ->
                updateState { it.copy(isDarkTheme = theme ?: false) }
            }
        }
    }

    fun onDismissThemeRequest() {
        updateState { it.copy(showBottomSheetTheme = false) }
    }

    fun onThemeClicked() {
        updateState { it.copy(showBottomSheetTheme = true, showBottomSheetLanguage = false) }
    }

    fun onThemeChanged(isDark: Boolean) {
        viewModelScope.launch {
            gradesFromGeeksRepository.setTheme(isDark)
            updateState { it.copy(isDarkTheme = isDark) }
        }
    }
    //endregion

    //region Language
    private fun getLanguage() {
        viewModelScope.launch {
            gradesFromGeeksRepository.getLanguage().distinctUntilChanged().collectLatest { lang ->
                updateState { it.copy(currentLanguage = lang) }
            }
        }
    }

    fun onLanguageClicked() {
        updateState { it.copy(showBottomSheetTheme = false, showBottomSheetLanguage = true) }
    }

    fun onDismissLanguageRequest() {
        updateState { it.copy(showBottomSheetLanguage = false) }
    }

    fun onLanguageChanged(selectedLanguage: Language) {
        viewModelScope.launch {
            gradesFromGeeksRepository.saveLanguage(selectedLanguage)
            updateState {
                it.copy(
                    currentLanguage = selectedLanguage,
                    showBottomSheetLanguage = false
                )
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            sendNewEffect(ProfileUIEffect.NavigateToSignIn)
        }
    }
    //endregion
}
