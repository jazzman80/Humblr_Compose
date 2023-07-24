package com.skillbox.humblr.database

import androidx.room.Dao
import androidx.room.Insert
import com.skillbox.humblr.entity.CommentDto

@Dao
interface CommentDao {

//    @Query("SELECT * FROM user")
//    fun selectAll(): List<User>

    @Insert
    suspend fun insert(comment: CommentDto)

//    @Delete
//    fun delete(user: User)
//
//    @Update
//    fun update(user: User)
}