package com.imandroid.simplefoursquare.screen.exploresList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imandroid.simplefoursquare.R
import com.imandroid.simplefoursquare.databinding.ItemExploreBinding
import com.imandroid.simplefoursquare.databinding.ItemLoadingBinding
import com.imandroid.simplefoursquare.domain.ExploreModel
import com.squareup.picasso.Picasso


/**
about using ListAdapter instead RecyclerView.adapter:

Advantage:

Less code
more animations
No need to worry about threading during comparing diffs

 */

class ExploreAdapter constructor(val listener: (Int) -> Unit) :
    ListAdapter<ExploreAdapter.DataItem, RecyclerView.ViewHolder>(ExploreDiffCallback()) {
     private val ITEM_LOADING = 0
     private val ITEM_EXPLORE = 1

    var isLoaderVisible: Boolean = false



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ExploreViewHolder -> {

                if (getItem(position) is DataItem.ExploreItem)
                {
                    val exploreItem = getItem(position) as DataItem.ExploreItem
                    holder.bind(exploreItem.explore)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM_EXPLORE -> ExploreViewHolder(ItemExploreBinding.inflate(layoutInflater, parent, false))
            ITEM_LOADING -> LoadingViewHolder(ItemLoadingBinding.inflate(layoutInflater, parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {

            if (position == currentList.size - 1) ITEM_LOADING else ITEM_EXPLORE
        } else when (getItem(position)) {
            is DataItem.ExploreItem -> ITEM_EXPLORE
            is DataItem.LoadingItem -> ITEM_LOADING
        }
    }


     fun submit(list: List<ExploreModel>?) {
            val items = when (list?.size) {
                0 -> listOf(DataItem.LoadingItem)
                else -> list?.map {
                    DataItem.ExploreItem(it)
                }
            }

                submitList(items)
    }


   inner class ExploreViewHolder(val binding: ItemExploreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExploreModel) {
            binding.explore = item
            binding.executePendingBindings()

            item.let {
                Picasso.get()
                    .load(item.photos.let { if (it?.isNotEmpty()!!){it[0]}else binding.imgItmExplore.context.getString(
                        R.string.url_img_default) })
                    .placeholder(R.drawable.placeholder)
                    .into(binding.imgItmExplore)
            }


            binding.root.setOnClickListener { listener(bindingAdapterPosition) }
        }

    }


    inner class LoadingViewHolder constructor(var binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {}


    fun addLoading() {
        isLoaderVisible = true
        val list  = mutableListOf<DataItem>()
        list.addAll(currentList)
        list.add(DataItem.LoadingItem)
        submitList(list)
    }



    fun removeLoading() {
        isLoaderVisible = false
        val list  = mutableListOf<DataItem>()
        list.addAll(currentList)
        list.remove(DataItem.LoadingItem)
        submitList(list)
    }



    class ExploreDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    sealed class DataItem {

        abstract val id: String

        data class ExploreItem(val explore: ExploreModel) : DataItem() {
            override val id = explore.explore_id
        }

        object LoadingItem : DataItem() {
            override val id = "loading"
        }

    }
}
