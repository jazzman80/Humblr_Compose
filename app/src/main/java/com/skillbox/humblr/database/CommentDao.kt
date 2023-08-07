package com.skillbox.humblr.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skillbox.humblr.entity.CommentDto

@Dao
interface CommentDao {

//    @Query("SELECT * FROM user")
//    fun selectAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: CommentDto)

    @Query("DELETE FROM comments")
    suspend fun clearTable()
//
//    @Update
//    fun update(user: User)
}