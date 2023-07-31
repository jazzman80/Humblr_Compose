package com.skillbox.humblr.database

import android.content.Context
import androidx.room.Room

object DatabaseModule {

    fun provideCommentDao(appDatabase: AppDatabase): CommentDao {
        return appDatabase.commentDao()
    }

    fun provideAppDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Database"
        )
            .build()
    }
}