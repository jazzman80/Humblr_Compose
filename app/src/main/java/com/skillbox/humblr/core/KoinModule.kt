package com.skillbox.humblr.core

import com.skillbox.humblr.database.AppDatabase
import com.skillbox.humblr.main.comments.CommentsViewModel
import com.skillbox.humblr.main.core.comments.ItemCommentViewModel
import com.skillbox.humblr.main.favorites.FavoritesViewModel
import com.skillbox.humblr.main.feed.FeedViewModel
import com.skillbox.humblr.main.posts.PostsViewModel
import com.skillbox.humblr.main.search.SearchViewModel
import com.skillbox.humblr.main.single_post.SinglePostViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::RepositoryImplementation) { bind<Repository>() }
    single {
        RetrofitModule.api
    }
    single { AppDatabase.getInstance(get()) }
    single { get<AppDatabase>().commentDao() }
    viewModelOf(::ItemCommentViewModel)
    viewModelOf(::SinglePostViewModel)
    viewModelOf(::PostsViewModel)
    viewModelOf(::CommentsViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FeedViewModel)
    viewModelOf(::FavoritesViewModel)
}