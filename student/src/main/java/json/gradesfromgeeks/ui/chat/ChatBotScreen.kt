package json.gradesfromgeeks.ui.chat

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import json.gradesFromGeeks.design_system.components.GGBottomSheetWithSearch
import json.gradesFromGeeks.design_system.components.GGToggleBottomSheetButton
import json.gradesFromGeeks.design_system.modifier.noRippleEffect
import json.gradesFromGeeks.design_system.theme.Gray_1
import json.gradesFromGeeks.design_system.theme.Theme
import json.gradesfromgeeks.ui.chat.composable.MessageCard
import json.gradesfromgeeks.ui.chat.composable.SendTextField
import json.gradesfromgeeks.R
import json.gradesfromgeeks.ui.utils.Utils
import json.gradesfromgeeks.ui.utils.Utils.readTextFromUri
import org.koin.androidx.compose.koinViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun ChatBotScreen(
    viewModel: ChatBotViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
) {

    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = null)

    val context = LocalContext.current
    PDFBoxResourceLoader.init(context)
    val pdfLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            val text = readTextFromUri(it, context)
            viewModel.onDocumentSelected(text)
        }
    }

    ChatBotContent(
        state = state,
        messageText = state.message,
        onValueChanged = viewModel::onChanceMessage,
        sendMessage = viewModel::onSendClicked,
        onCLickBack = onNavigateBack,
        onSelectUniversity = viewModel::onSelectUniversity,
        onDismissRequest = viewModel::onDismissRequest,
        onDocumentSelected = { pdfLauncher.launch(arrayOf("application/pdf")) },
    )

}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ChatBotContent(
    state: ChatUiState,
    messageText: String,
    onValueChanged: (String) -> Unit,
    sendMessage: () -> Unit,
    onCLickBack: () -> Unit,
    onDismissRequest: () -> Unit,
    onSelectUniversity: (Int) -> Unit,
    onDocumentSelected:()->Unit,

    ) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(1.dp),
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onCLickBack() }) {
                        Icon(
                            painterResource(com.solutionteam.design_system.R.drawable.arrow),
                            contentDescription = "Go back",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .noRippleEffect { onCLickBack() }
                                .rotate(180f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Theme.colors.background
                ),
                actions = {
                    if (!state.isFirstEnter) {
                        if (!state.isDocumentMode){
                            GGToggleBottomSheetButton(
                                modifier = Modifier.width(200.dp),
                                value = state.universityName,
                                onValueChanged = onDismissRequest,
                                isOpen = state.isUniversitySheetOpen,
                                onToggle = onDismissRequest,
                                hint = "Select University",
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    cursorColor = Gray_1,
                                    errorCursorColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    errorBorderColor = Color.Transparent,
                                )
                            )
                        }

                        if (state.isDocumentMode) {
                            Text("Document attached ✓", color = Color.Green)
                        }

                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),

            ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.background_chat_screen),
                contentDescription = "background chat screen",
                contentScale = ContentScale.Crop,
            )
            AnimatedVisibility(visible = state.isFirstEnter) {
                if (state.showSourceSelector) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(0.7f),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .background(color = Theme.colors.card, shape = CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.chat_bot_background),
                                contentDescription = "icon chat bot",
                                modifier = Modifier.padding(top = 16.dp),
                                tint = Theme.colors.primary
                            )
                        }
                        Text(
                            text = stringResource(R.string.start_chatting_with_chat_bot_now),
                            style = Theme.typography.titleSmall,
                            color = Theme.colors.primaryShadesDark,
                            modifier = Modifier.padding(top = 32.dp)
                        )
                        Text(
                            text = stringResource(R.string.by_select_the_university_you_want_to_learn_more_about_and_obtain_references_from),
                            style = Theme.typography.bodyLarge,
                            color = Theme.colors.ternaryShadesDark,
                            modifier = Modifier.padding(top = 8.dp, end = 38.dp, start = 38.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        GGToggleBottomSheetButton(
                            modifier = Modifier
                                .width(250.dp)
                                .padding(top = 28.dp),
                            value = state.universityName,
                            onValueChanged = onDismissRequest,
                            isOpen = state.isUniversitySheetOpen,
                            onToggle = onDismissRequest,
                            hint = "Select University",
                        )

                        Divider(
                            thickness = 1.5.dp,
                            modifier = Modifier.padding(top = 16.dp, start = 45.dp, end = 45.dp),
                            color = Theme.colors.primary.copy(alpha = 0.5f)
                        )

                        Text(
                            text = "or_by_raising_your_reference_and_answering_the_question_from_it)",
                            style = Theme.typography.bodyLarge,
                            color = Theme.colors.ternaryShadesDark,
                            modifier = Modifier.padding(top = 8.dp, end = 38.dp, start = 38.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Button(
                            onClick = onDocumentSelected,
                            modifier = Modifier
                                .width(250.dp)
                                .height(84.dp)
                                .padding(top = 28.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            )
                        ) {
                            Text(
                                "Upload your pdf",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                style = MaterialTheme.typography.labelLarge,
                                color = Theme.colors.primaryShadesDark.copy(alpha = 0.6f)
                            )
                        }

                    }}
                if (state.isDocumentMode) {
                    Text("Document attached ✓", color = Color.Green)
                }
            }
            AnimatedVisibility(visible = !state.isFirstEnter) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Bottom,
                        contentPadding = PaddingValues(vertical = 24.dp),
                    ) {

                        item {
                            if (state.selectedUniversity in state.universities.indices) {
                                Text(
                                    text = state.universities[state.selectedUniversity],
                                    style = Theme.typography.bodyLarge,
                                    color = Theme.colors.ternaryShadesDark,
                                    modifier = Modifier
                                        .padding(top = 8.dp, end = 38.dp, start = 38.dp)
                                        .fillMaxWidth(),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            } else if (state.isDocumentMode) {
                                Text(
                                    text = "Document Mode: ${state.documentText.take(20)}...",
                                    style = Theme.typography.bodyLarge,
                                    color = Theme.colors.ternaryShadesDark,
                                    modifier = Modifier
                                        .padding(top = 8.dp, end = 38.dp, start = 38.dp)
                                        .fillMaxWidth(),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }

                        items(items = state.messages) {
                            MessageCard(it, Modifier.animateItemPlacement())
                        }

                    }
                    SendTextField(
                        text = messageText,
                        onValueChanged = onValueChanged,
                        sendMessage = sendMessage,
                        canMessage = state.canNotSendMessage
                    )
                }
            }
        }

    }
    UniversityBottomSheet(
        state = state,
        onSelectUniversity = onSelectUniversity,
        onDismissRequest = onDismissRequest,
    )

    LaunchedEffect(key1 = true) {
        listState.animateScrollToItem(index = state.messages.lastIndexOrZero())
    }

}

@Composable
private fun UniversityBottomSheet(
    state: ChatUiState,
    modifier: Modifier = Modifier,
    onSelectUniversity: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (state.isUniversitySheetOpen && !state.hasSelectedSource) {
        GGBottomSheetWithSearch(
            modifier = modifier,
            items = state.universities,
            onItemSelected = { university, index ->
                onSelectUniversity(index)
            },
            onDismissRequest = onDismissRequest
        )
    }
}

fun <T> List<T>.lastIndexOrZero(): Int {
    return if (this.isEmpty()) 0 else this.size - 1
}

