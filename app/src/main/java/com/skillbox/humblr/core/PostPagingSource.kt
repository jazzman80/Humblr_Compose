package com.skillbox.humblr.core

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.humblr.entity.Post
import retrofit2.awaitResponse

class PostPagingSource(
    private val auth: String,
    private val apiService: ApiService,
    private val limit: Int,
    private val title: String
) : PagingSource<String, Post>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {

        try {
            val result = apiService.getPosts(
                auth,
                title,
                params.key,
                limit
            ).awaitResponse()

            if (result.isSuccessful) {
                return LoadResult.Page(
                    data = result.body()!!.data.children,
                    prevKey = null,
                    nextKey = result.body()!!.data.after
                )
            } else {
                return LoadResult.Error(throw Exception())
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.data!!.id }
    }
}