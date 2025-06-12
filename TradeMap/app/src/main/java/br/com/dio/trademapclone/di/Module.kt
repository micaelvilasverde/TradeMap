package br.com.dio.trademapclone.di

import br.com.dio.trademapclone.AppDatabase
import br.com.dio.trademapclone.MainViewModel
import br.com.dio.trademapclone.RetrofitService
import br.com.dio.trademapclone.repository.AcaoRepository
import br.com.dio.trademapclone.ui.AcaoViewModel
import br.com.dio.trademapclone.ui.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::AcaoViewModel)
}

val serviceModule = module {
    single { RetrofitService.createService() }
}

val repositoryModule = module {
    singleOf(::AcaoRepository)
}

val daoModule = module {
    single { AppDatabase.getInstance(androidContext()).acaoDAO() }
}