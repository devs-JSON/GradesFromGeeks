package json.gradesfromgeeks.ui.downloads

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import json.gradesFromGeeks.design_system.components.GGAppBar
import json.gradesFromGeeks.design_system.components.GGTabBar
import json.gradesFromGeeks.design_system.theme.Theme
import json.gradesfromgeeks.R
import json.gradesfromgeeks.ui.sharedComposable.ContentCountCard
import json.gradesfromgeeks.ui.sharedComposable.MeetingScreen
import json.gradesfromgeeks.ui.sharedComposable.SubjectComposable
import json.gradesfromgeeks.ui.sharedComposable.SummeryScreen
import json.gradesfromgeeks.ui.university.ContentCountUIState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun DownloadsScreen(
    viewModel: DownloadsViewModel = koinViewModel(),
    onNavigateTo: (DownloadsUIEffect) -> Unit
) {

    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = null)
    val context = LocalContext.current

    DownloadContent(
        state = state,
        onNavigateToReviewScreen = { onNavigateTo(DownloadsUIEffect.NavigateToReviewScreen) },
        onNavigateToPDFReader = { onNavigateTo(DownloadsUIEffect.NavigateToPDFReader) }
    )

    LaunchedEffect(key1 = state.isSuccess) {
        viewModel.effect.collectLatest {
            onEffect(effect, context)
        }
    }
}


private fun onEffect(effect: DownloadsUIEffect?, context: Context) {

    when (effect) {
        DownloadsUIEffect.DownloadsError -> Toast.makeText(context, "error", Toast.LENGTH_SHORT)
            .show()

        else -> {}
    }
}


@Composable
private fun DownloadContent(
    state: DownloadsUIState,
    onNavigateToReviewScreen: () -> Unit,
    onNavigateToPDFReader: () -> Unit
) {

    Scaffold(
        topBar = {
            GGAppBar(
                title = stringResource(id = R.string.download_title),
                showNavigationIcon = false
            )
        },
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()
                .background(Theme.colors.background),
                contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding())
                    .background(Theme.colors.background)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    ContentCountCard(
                        contentCountList = contentCountList(state.downloadDetails),
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    SubjectComposable(subjectList = state.downloadDetails.subjectList)

                    GGTabBar(
                        tabs = listOf(
                            stringResource(id = R.string.summaries) to {
                                SummeryScreen(
                                    summeryList = state.downloadDetails.summaryList,
                                    onNavigate = onNavigateToPDFReader
                                )
                            },
                            stringResource(id = R.string.videos) to {
                                SummeryScreen(
                                    onNavigate = onNavigateToReviewScreen,
                                    summeryList = state.downloadDetails.videoList,
                                    isVideo = true,
                                )
                            },
                            stringResource(id = R.string.meetings) to {
                                MeetingScreen(
                                    onClickMeeting = {},
                                    meetingList = state.downloadDetails.meetingList
                                )
                            }
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun contentCountList(state: DownloadDetailsUIState) = listOf(
    ContentCountUIState(
        state.summaryNumber,
        stringResource(id = R.string.summaries)
    ),
    ContentCountUIState(
        state.videoNumber,
        stringResource(id = R.string.videos)
    ),
    ContentCountUIState(
        state.meetingNumber,
        stringResource(id = R.string.meetings)
    )
)
