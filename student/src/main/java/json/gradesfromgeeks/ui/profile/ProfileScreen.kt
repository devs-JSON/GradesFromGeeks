package json.gradesfromgeeks.ui.profile

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import json.gradesFromGeeks.design_system.components.GGAppBar
import json.gradesFromGeeks.design_system.components.GGPreferencesCard
import json.gradesFromGeeks.design_system.theme.Theme
import json.gradesfromgeeks.R
import json.gradesfromgeeks.data.entity.Language
import json.gradesfromgeeks.ui.profile.component.ProfileCard
import json.gradesfromgeeks.ui.profile.component.LanguageBottomSheet
import json.gradesfromgeeks.ui.profile.component.ThemeBottomSheet
import json.gradesfromgeeks.ui.utils.updateLanguage
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    navigateTo: (ProfileUIEffect) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = null)
    val context = LocalContext.current

    ProfileContent(
        state = state,
        onDismissThemeRequest = viewModel::onDismissThemeRequest,
        onThemeClicked = viewModel::onThemeClicked,
        onThemeChanged = viewModel::onThemeChanged,
        onLanguageClicked = viewModel::onLanguageClicked,
        onDismissLanguageRequest = viewModel::onDismissLanguageRequest,
        onLogout = {
            viewModel.onLogout()
            navigateTo(ProfileUIEffect.NavigateToSignIn)
          },
        onLanguageChanged = {
            updateLanguage(context = context, language = it.abbreviation)
            viewModel.onLanguageChanged(it)
        }
    )

    LaunchedEffect(key1 = state.isSuccess) {
        viewModel.effect.collectLatest {
            onEffect(effect, context)
        }
    }
}


private fun onEffect(effect: ProfileUIEffect?, context: Context) {
    when (effect) {
        ProfileUIEffect.ProfileError -> Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
        else -> {}
    }
}


@Composable
private fun ProfileContent(
    state: ProfileUIState,
    onDismissThemeRequest: () -> Unit,
    onThemeClicked: () -> Unit,
    onThemeChanged: (Boolean) -> Unit,
    onLanguageClicked: () -> Unit,
    onDismissLanguageRequest: () -> Unit,
    onLogout: () -> Unit,
    onLanguageChanged: (Language) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Theme.colors.background),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            GGAppBar(
                title = stringResource(id = R.string.profile_title),
                showNavigationIcon = false
            )

            ProfileCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                profileUrl = state.profileUrl?:"",
                name = state.name
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.preferences),
                style = Theme.typography.titleSmall,
                color = Theme.colors.primaryShadesDark
            )

            GGPreferencesCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = stringResource(id = R.string.theme),
                painter = painterResource(id = R.drawable.theme),
                onClick = onThemeClicked
            )

            GGPreferencesCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                title = stringResource(id = R.string.language),
                painter = painterResource(id = R.drawable.planet),
                onClick = onLanguageClicked
            )
        }

        if (state.showBottomSheetTheme) {
            ThemeBottomSheet(
                onDismissRequest = onDismissThemeRequest,
                onThemeChanged = onThemeChanged,
                isDarkTheme = state.isDarkTheme
            )
        }

        if (state.showBottomSheetLanguage) {
            LanguageBottomSheet(
                onDismissRequest = onDismissLanguageRequest,
                onLanguageChanged = onLanguageChanged,
                languages = state.languages,
                selectedLanguage = state.currentLanguage
            )
        }
    }
}
