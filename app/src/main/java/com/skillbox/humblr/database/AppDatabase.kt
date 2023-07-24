package com.skillbox.humblr.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.humblr.entity.CommentDto

@Database(entities = [CommentDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun commentDao(): CommentDao
}