package com.skillbox.humblr.core

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.humblr.entity.Thing
import retrofit2.awaitResponse

class CommentsPagingSource(
    private val article: String,
    private val auth: String,
    private val apiService: ApiService,
    private val limit: Int
) : PagingSource<String, Thing>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Thing> {

        try {
            val result = apiService.getPostWithComment(
                article = article,
                auth = auth,
                after = params.key,
                limit = limit
            ).awaitResponse()

            return if (result.isSuccessful) {
                LoadResult.Page(
                    data = result.body()!![1].data.children,
                    prevKey = null,
                    nextKey = result.body()!![1].data.after
                )
            } else {
                LoadResult.Error(throw Exception())
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Thing>): String? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.data!!.id }
    }
}