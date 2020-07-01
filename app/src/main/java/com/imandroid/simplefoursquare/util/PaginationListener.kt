package com.imandroid.simplefoursquare.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class PaginationListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            /**
            check is user scroll to the end of recyclerView?
            and do the pagination and fetch new data
             */
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                Timber.i("loadMoreItems in pagination listener...")

                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()

    companion object {
        val FIRST_PAGE = 0
        /**
         * Set scrolling threshold here (X item in one page)
         */
        internal const val PAGE_SIZE = 15
    }
}