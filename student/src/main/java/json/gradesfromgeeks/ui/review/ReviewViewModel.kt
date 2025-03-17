package json.gradesfromgeeks.ui.review

import android.net.Uri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.ui.base.BaseViewModel


class ReviewViewModel(
    private val mindfulMentorRepository: GradesFromGeeksRepository,
    val player: Player,
) : BaseViewModel<ReviewUIState, ReviewUIEffect>(ReviewUIState()) {

    init {
        onMakeRequest()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
    private fun onMakeRequest() {
        updateState { it.copy(isLoading = true) }
        tryToExecute(
                mindfulMentorRepository::getVideoUrl,
                ::onSuccess,
                ::onError
        )
    }


    private fun onSuccess(videoUri: String) {
        player.setMediaItem(MediaItem.fromUri(Uri.parse(videoUri)))
        player.prepare()
        updateState { it.copy(isSuccess = true, isError = false, isLoading = false) }
    }

    private fun onError() {
        updateState {
            ReviewUIState(
                    isError = true,
                    isLoading = false,
                    isSuccess = false
            )
        }
        sendNewEffect(ReviewUIEffect.ReviewError)
    }

    fun onClickFullVideoScreen(isFullScreen: Boolean) {
        updateState { it.copy(isVideoFullScreen = isFullScreen) }
    }

}
