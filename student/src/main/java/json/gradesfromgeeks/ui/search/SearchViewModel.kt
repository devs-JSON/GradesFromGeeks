package json.gradesfromgeeks.ui.search

import androidx.lifecycle.viewModelScope
import json.gradesfromgeeks.data.entity.SearchResult
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val mindfulMentor: GradesFromGeeksRepository
) : BaseViewModel<SearchUIState, SearchUIEffect>(SearchUIState()) {

    private var searchJob: Job? = null

    fun onSearchTextChange(keyword: String) {
       updateState { it.copy(keyword = keyword) }
       onSearch()
    }

    private fun onSearch() {
        updateState { it.copy(isLoading = true, error = "") }
        searchJob?.cancel()
        searchJob = tryToExecute(
            {   delay(1000)
                mindfulMentor.getSearch(state.value.keyword.trim(),3)
            },
                {onSuccess(it)},
            ::onError
        )
    }

    private fun onSuccess(result: SearchResult) {
        viewModelScope.launch {
            _state.emit(result.toUIState().copy(keyword = state.value.keyword))
        }
    }

    private fun onError() {
        updateState {
            it.copy(
                    isLoading = false,
                    error = "error",
                    mentors = emptyList(),
                    universities = emptyList()
            )
        }
        sendNewEffect(SearchUIEffect.SearchError)
    }


}
