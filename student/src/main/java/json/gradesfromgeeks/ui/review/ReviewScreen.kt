package json.gradesfromgeeks.ui.review

import RatingStar
import android.content.Context
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.exoplayer2.Player
import json.gradesFromGeeks.design_system.components.GGAppBar
import json.gradesFromGeeks.design_system.components.GGButton
import json.gradesFromGeeks.design_system.components.GGMentor
import json.gradesFromGeeks.design_system.components.GGPreferencesCard
import json.gradesFromGeeks.design_system.components.GGTabBar
import json.gradesFromGeeks.design_system.components.GGTextField
import json.gradesFromGeeks.design_system.components.setStatusBarColor
import json.gradesFromGeeks.design_system.theme.PlusJakartaSans
import json.gradesFromGeeks.design_system.theme.Theme
import json.gradesfromgeeks.R
import json.gradesfromgeeks.data.entity.Summaries
import json.gradesfromgeeks.ui.review.composable.VideoLayout
import json.gradesfromgeeks.utils.setScreenOrientation
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ReviewScreen(
    viewModel: ReviewViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = null)
    val context = LocalContext.current
    val systemUIController = rememberSystemUiController()

    ReviewContent(
            state = state,
            onBack = navigateBack,
            player = viewModel.player,
            onClickFullVideoScreen = viewModel::onClickFullVideoScreen,
            onClickSummaries = viewModel::onClickSummaries
    )

    val color = Theme.colors.background
    LaunchedEffect(true) {
        setStatusBarColor(
            systemUIController = systemUIController,
            navigationBarColor = color,
            statusBarColor = color,
            isDarkIcon = true
        )
    }

    LaunchedEffect(key1 = state.isSuccess) {
        viewModel.effect.collectLatest {
            onEffect(effect, context)
        }
    }

}


private fun onEffect(effect: ReviewUIEffect?, context: Context) {

    when (effect) {
        ReviewUIEffect.ReviewError -> Toast.makeText(context, "error", Toast.LENGTH_SHORT)
            .show()

        else -> {}
    }
}


@Composable
private fun ReviewContent(
    state: ReviewUIState,
    onClickFullVideoScreen: (Boolean) -> Unit,
    onClickSummaries: () -> Unit,
    onBack: () -> Unit,
    player: Player
) {
    val context = LocalContext.current

    BackHandler {
        if (state.isVideoFullScreen) {
            onClickFullVideoScreen(false)
            context.setScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        } else {
            onBack()
        }
    }
    Scaffold(
            topBar = {
                if (!state.isVideoFullScreen) {
                    GGAppBar(title = "Data Structure-lecture 1", onBack = onBack)
                }
            }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Theme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                VideoLayout(
                        exoPlayer = player,
                        onClick = onClickFullVideoScreen,
                        modifier = if (state.isVideoFullScreen) Modifier.fillMaxSize() else Modifier.aspectRatio(
                                16 / 9f
                        )
                )

                GGTabBar(
                    tabs = listOf(
                        "Info" to { InfoComposable() },
                        "Review" to { ReviewComposable() },
                        "Summarize" to { SummarizeComposable(
                            summaries = state.summaries,
                            onClickSummaries = onClickSummaries
                        ) }
                    ),
                )
            }
        }
    }
}


@Composable
private fun InfoComposable() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        GGMentor(
            modifier = Modifier.padding(16.dp),
            name = "Amnah Ali",
            isForSubject = true,
            subjectName = "Data Structure - lecture 1",
            profileUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_p4wGt_hng5BeADmgd6lf0wPrY6aOssc3RA&usqp=CAU",
            onClick = {}
        )

        GGPreferencesCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = stringResource(id = R.string.help),
            painter = painterResource(id = R.drawable.ic_help),
            onClick = {}
        )
    }
}


@Composable
private fun ReviewComposable() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        var rating by remember { mutableFloatStateOf(4.5f) }

        RatingStar(
            rating = rating,
            maxRating = 5,
            onStarClick = {
                rating = it.toFloat()
            }, false
        )

        var text by remember { mutableStateOf("") }

        GGTextField(
            text = text,
            onValueChange = {
                text = it
            },
            hint = "Write Your Review",
            textStyle = TextStyle(
                fontFamily = PlusJakartaSans,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 22.sp,
            ),
            modifier = Modifier
                .padding(16.dp),
            focusedBorderColor = Color.Transparent,
            textFieldModifier = Modifier
                .fillMaxHeight(.3f)
                .fillMaxWidth(),
            shapeRadius = RoundedCornerShape(20.dp),
            singleLine = false
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
            GGButton(
                title = "Send",
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth(.3f)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}


@Composable
private fun SummarizeComposable(
    summaries: String = "",
    onClickSummaries: () -> Unit
) {
    if (summaries.isNotEmpty()) {
        Divider(
            color = Theme.colors.quaternaryShadesDark,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            item {
                FormattedText(summaries)
            }
        }
    } else {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_summarize),
                contentDescription = "summarize_icon",
                modifier = Modifier.padding(top = 45.dp)
            )

            Text(
                text = stringResource(id = R.string.nothing_to_show),
                style = Theme.typography.bodyLarge,
                color = Theme.colors.primary
            )

            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(id = R.string.click_summarize),
                style = Theme.typography.labelLarge,
                textAlign = TextAlign.Center,
                color = Theme.colors.primary
            )

            GGButton(
                title = "Summarize",
                onClick = onClickSummaries,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            )

        }
    }

}


@Composable
private fun FormattedText(textFromApi: String) {
    val lines = textFromApi.split("\n").map { it.trim() }.filter { it.isNotEmpty() }

    Column(modifier = Modifier.padding(16.dp)) {
        lines.forEachIndexed { index, line ->
            when {
                line.startsWith("*   **") -> {
                    Row(modifier = Modifier.padding(bottom = 4.dp)) {
                        Text(text = "○ ",
                            style = Theme.typography.labelLarge,
                            color = Theme.colors.primary,
                        )

                        Text(text = line.replace("**"," ").replace("*","").trim(),
                            style = Theme.typography.labelLarge,
                            color = Theme.colors.primaryShadesDark,)
                    }
                }

                line.contains("**") -> {
                    Text(
                        text = line.replace("**", ""),
                        style = Theme.typography.labelLarge,
                        color = Theme.colors.primaryShadesDark,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )

                }


                line.startsWith("*") -> {
                    Row(modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)) {
                        Text(text = "○  ",
                            style = Theme.typography.labelLarge,
                            color = Theme.colors.primary,
                        )

                        Text(text = line.removePrefix("*").trim(),
                            style = Theme.typography.labelLarge,
                            color = Theme.colors.primaryShadesDark,)
                    }
                }

                else -> {
                    Text(
                        text = line,
                        style = Theme.typography.labelLarge,
                        color = Theme.colors.primaryShadesDark,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )
                }
            }
        }
    }
}
