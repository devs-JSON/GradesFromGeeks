package json.gradesfromgeeks.ui.downloads

import json.gradesfromgeeks.data.entity.Download
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel
import kotlinx.coroutines.delay

class DownloadsViewModel(
    private val ggRepository: GradesFromGeeksRepository
) : BaseViewModel<DownloadsUIState, DownloadsUIEffect>(DownloadsUIState()) {

    init {
        getDataOfDownload()
    }

    private fun getDataOfDownload() {
        updateState { it.copy(isLoading = true) }
        onGetDownloadDetails()
    }

    private fun onGetDownloadDetails() {
        tryToExecute(
            {
                delay(1000)
                ggRepository.getDownloadDetails("1")
            },
            ::onSuccess,
            ::onError
        )
    }

    private fun onSuccess(download: Download) {
        updateState {
            it.copy(
                isSuccess = true,
                isError = false,
                isLoading = false,
                downloadDetails = download.toUIState()
            )
        }
    }

    private fun onError() {
        updateState {
            DownloadsUIState(
                isError = true,
                isLoading = false,
                isSuccess = false
            )
        }
        sendNewEffect(DownloadsUIEffect.DownloadsError)
    }


}
