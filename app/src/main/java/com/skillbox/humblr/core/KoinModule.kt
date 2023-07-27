package com.skillbox.humblr.core

import com.skillbox.humblr.database.DatabaseModule
import com.skillbox.humblr.main.core.comments.ItemCommentViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::RepositoryImplementation) { bind<Repository>() }
    single {
        RetrofitModule.api
    }
    single {
        DatabaseModule.provideAppDatabase(get())
    }
    single {
        DatabaseModule.provideCommentDao(get())
    }
    viewModelOf(::ItemCommentViewModel)

}