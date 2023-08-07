package com.skillbox.humblr.core.PagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.humblr.core.ApiService
import com.skillbox.humblr.entity.Subreddit
import retrofit2.awaitResponse

class PopularSubsPagingSource(
    private val auth: String,
    private val apiService: ApiService,
    private val limit: Int
) : PagingSource<String, Subreddit>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Subreddit> {

        try {
            val result = apiService.getPopularSubs(
                auth,
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

    override fun getRefreshKey(state: PagingState<String, Subreddit>): String? {
        return state.anchorPosition?.let { state.closestItemToPosition(it)?.data!!.id }
    }
}