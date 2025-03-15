package json.gradesfromgeeks.di

import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepository
import json.gradesfromgeeks.data.repositories.GradesFromGeeksRepositoryImp
import org.koin.dsl.module

val RepositoryModule = module {
    single<GradesFromGeeksRepository> { GradesFromGeeksRepositoryImp(get()) }
}
