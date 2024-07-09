package com.peacepark.clipy.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.peacepark.clipy.item.HomeItem
import com.peacepark.clipy.type.MainItemType
import com.peacepark.clipy.R
import com.peacepark.clipy.databinding.ItemBusinessCardMainBinding
import com.peacepark.clipy.databinding.ItemQrCodeMainBinding
import com.peacepark.clipy.databinding.ItemUnknownMainBinding

class ImagePagerAdapter(
    private val moveCreatePage: () -> Unit,
): ListAdapter<HomeItem, ImagePagerAdapter.ItemViewHolder>(object : DiffUtil.ItemCallback<HomeItem>() {
    override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return when {
            oldItem.cardUri != null && newItem.cardUri != null -> {
                oldItem.cardUri == newItem.cardUri
            }
            oldItem.qrBitmap != null && newItem.qrBitmap != null -> {
                oldItem.qrBitmap == newItem.qrBitmap
            }
            else -> {
                false
            }
        }
    }

    override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
        return oldItem == newItem
    }
}) {
    abstract class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: HomeItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return when (viewType) {
            MainItemType.BUSINESS_CARD.ordinal -> {
                BusinessCardItemViewHolder(
                    ItemBusinessCardMainBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    moveCreatePage = moveCreatePage
                )
            }

            MainItemType.QR_CODE.ordinal -> {
                QRCodeItemViewHolder(
                    ItemQrCodeMainBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                UnknownItemViewHolder(
                    ItemUnknownMainBinding.inflate(
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

    class BusinessCardItemViewHolder(
        private val binding: ItemBusinessCardMainBinding,
        private val moveCreatePage: () -> Unit
    ) : ItemViewHolder(binding.root) {
        override fun onBind(item: HomeItem) {
            binding.ivBusinessCard.load(item.cardUri) {
                error(R.drawable.img_logo)
            }

            binding.ivBusinessCard.setOnClickListener {
                moveCreatePage()
            }
        }
    }

    class QRCodeItemViewHolder(private val binding: ItemQrCodeMainBinding) : ItemViewHolder(binding.root) {
        override fun onBind(item: HomeItem) {
            if (item.qrBitmap != null) {
                binding.ivQrCode.load(item.qrBitmap)
            } else {
                binding.ivQrCode.load(item.cardUri) {
                    error(R.drawable.img_logo)
                }
            }
        }
    }

    class UnknownItemViewHolder(private val binding: ItemUnknownMainBinding) : ItemViewHolder(binding.root) {
        override fun onBind(item: HomeItem) = Unit
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isCard) {
            MainItemType.BUSINESS_CARD.ordinal
        } else {
            MainItemType.QR_CODE.ordinal
        }
    }
}