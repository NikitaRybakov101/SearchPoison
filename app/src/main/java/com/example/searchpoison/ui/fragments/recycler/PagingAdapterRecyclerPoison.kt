package com.example.searchpoison.ui.fragments.recycler

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.searchpoison.databinding.ItemRecyclerPoisonBinding
import com.example.searchpoison.repository.dataSourse.BASE_URL_API
import com.example.searchpoison.repository.dataSourse.Poison

class PagingAdapterRecyclerPoison(private val callbackItem: CallbackItem) : PagingDataAdapter<Poison,PagingAdapterRecyclerPoison.ViewHolderItemsPoison>(ResponsePoisonDiffItemCallback) {

    companion object {
        private const val CARD_ELEVATION = 0f
        const val PAGE_SIZE = 6
    }

    private var _binding : ItemRecyclerPoisonBinding? = null
    private val binding : ItemRecyclerPoisonBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemsPoison {
        _binding = ItemRecyclerPoisonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderItemsPoison(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderItemsPoison, position: Int) {
        holder.bind(getItem(position))
    }

    private object ResponsePoisonDiffItemCallback : DiffUtil.ItemCallback<Poison>() {
        override fun areItemsTheSame(oldItem: Poison, newItem: Poison): Boolean {
            return oldItem != newItem
        }

        override fun areContentsTheSame(oldItem: Poison, newItem: Poison): Boolean {
            return oldItem.name == newItem.name && oldItem.imageUri == newItem.imageUri
        }
    }

    inner class ViewHolderItemsPoison(private val bindingViewHolder: ItemRecyclerPoisonBinding) : RecyclerView.ViewHolder(binding.root), InterfaceViewHolderNotes {

        override fun itemClicked(poison: Poison) {
            bindingViewHolder.cardItems.setOnClickListener {
                callbackItem.itemClicked(poison.id)
            }
        }

        override fun bind(poison: Poison?)  {
            poison?.let {
                itemClicked(poison)

                with(bindingViewHolder) {
                    header.text = poison.name
                    descriptionItem.text = poison.description
                    cardItems.elevation = CARD_ELEVATION

                    imageItem.load(BASE_URL_API + poison.imageUri) {
                        placeholder(ColorDrawable(Color.TRANSPARENT))
                    }

                    imageItemBack.load(BASE_URL_API + poison.categories.imageUBackUrl) {
                        placeholder(ColorDrawable(Color.TRANSPARENT))
                    }
                }
            }
        }
    }

    interface InterfaceViewHolderNotes {
        fun itemClicked(poison: Poison)
        fun bind(poison: Poison?)
    }
}