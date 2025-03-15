package json.gradesfromgeeks.ui.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.view.WindowManager.LayoutParams
import json.gradesFromGeeks.design_system.theme.Theme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE)
        installSplashScreen()
        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = Theme.colors.background,
                content = {
                    App()
                }
            )
        }
    }
}

