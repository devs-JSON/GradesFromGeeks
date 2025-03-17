package json.gradesfromgeeks.di

import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


fun appModule() = module {

    includes(
        AiModel,
        RepositoryModule,
        viewModelModule,
        VideoPlayerModule
    )

    single<Context> { androidApplication().applicationContext }

}
