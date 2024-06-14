package com.example.clipy.ui.storage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.clipy.databinding.ItemBusinessCardStorageBinding
import com.example.clipy.databinding.ItemUnknownStorageBinding
import com.example.clipy.item.StorageItem
import com.example.clipy.type.StorageItemType

class StorageListAdapter(
    private val moveDetailPage: () -> Unit
) : ListAdapter<StorageItem, StorageListAdapter.ItemViewHolder>(object :
    DiffUtil.ItemCallback<StorageItem>() {
    override fun areItemsTheSame(oldItem: StorageItem, newItem: StorageItem): Boolean {
        return oldItem.businessCard == newItem.businessCard && oldItem.name == newItem.name
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
        holder.onBind(getItem(position))
    }

    class DetailItemViewHolder(
        private val binding: ItemBusinessCardStorageBinding,
        private val moveDetailPage: () -> Unit
    ) : ItemViewHolder(binding.root) {
        override fun onBind(item: StorageItem) = with(binding) {
            tvItemName.text = item.name
            tvItemRank.text = item.rank
            tvItemCompany.text = item.company
            ivItemBusinessCard.load(item.businessCard)
            if (item.badge) ivItemAuthBadge.isVisible = true

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