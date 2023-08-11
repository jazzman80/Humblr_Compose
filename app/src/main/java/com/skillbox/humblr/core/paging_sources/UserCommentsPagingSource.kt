package com.skillbox.humblr.core.paging_sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.humblr.core.ApiService
import com.skillbox.humblr.entity.CommentDto
import retrofit2.awaitResponse

class UserCommentsPagingSource(
    private val username: String,
    private val auth: String,
    private val apiService: ApiService,
    private val limit: Int
) : PagingSource<String, CommentDto>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CommentDto> {

        try {
            val result = apiService.getUserComments(
                username = username,
                auth = auth,
                after = params.key,
                limit = limit
            ).awaitResponse()

            return if (result.isSuccessful) {

                val data = result.body()!!.data.children
                val convertedData = mutableListOf<CommentDto>()

                for (item in data) {
                    convertedData.add(item.data.toCommentDto())
                }

                LoadResult.Page(
                    data = convertedData,
                    prevKey = null,
                    nextKey = result.body()!!.data.after
                )
            } else {
                LoadResult.Error(throw Exception())
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, CommentDto>): String? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.id }
    }
}