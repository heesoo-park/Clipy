package com.peacepark.clipy.ui.storage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.peacepark.clipy.type.StorageItemType
import com.peacepark.clipy.databinding.ItemBusinessCardStorageBinding
import com.peacepark.clipy.databinding.ItemUnknownStorageBinding
import com.peacepark.clipy.item.StorageItem
import com.peacepark.domain.model.Card

class StoragePagingAdapter(
    private val moveDetailPage: () -> Unit
) : PagingDataAdapter<StorageItem, StoragePagingAdapter.ItemViewHolder>(object :
    DiffUtil.ItemCallback<StorageItem>() {
    override fun areItemsTheSame(oldItem: StorageItem, newItem: StorageItem): Boolean {
        return oldItem.cardCode == newItem.cardCode && oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: StorageItem, newItem: StorageItem): Boolean {
        return oldItem == newItem
    }
}) {
    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: StorageItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return when (viewType) {
            StorageItemType.BUSINESS_CARD.ordinal -> {
                DetailItemViewHolder(
                    ItemBusinessCardStorageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    moveDetailPage = moveDetailPage
                )
            }

            else -> {
                UnknownItemViewHolder(
                    ItemUnknownStorageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    class DetailItemViewHolder(
        private val binding: ItemBusinessCardStorageBinding,
        private val moveDetailPage: () -> Unit
    ) : ItemViewHolder(binding.root) {
        override fun onBind(item: StorageItem) = with(binding) {
            tvItemName.text = item.name
            tvItemRank.text = item.rank
            tvItemCompany.text = item.company
            ivItemBusinessCard.load(item.cardImage)
//            if (item.badge) ivItemAuthBadge.isVisible = true

            itemView.setOnClickListener {
                moveDetailPage.invoke()
            }
        }
    }

    class UnknownItemViewHolder(
        private val binding: ItemUnknownStorageBinding
    ) : ItemViewHolder(binding.root) {
        override fun onBind(item: StorageItem) = Unit
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is StorageItem) {
            StorageItemType.BUSINESS_CARD.ordinal
        } else {
            StorageItemType.UNKNOWN.ordinal
        }
    }
}