package com.example.searchpoison.ui.fragments.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchpoison.R
import com.example.searchpoison.databinding.ItemRecyclerPoisonBinding
import com.example.searchpoison.repository.dataSourse.BASE_URL_API
import com.example.searchpoison.repository.dataSourse.Poison

class PagingAdapterRecyclerPoison(
    private var listPoisons: List<Poison>,
    private val callbackItem: CallbackItem

) : PagingDataAdapter<Poison,PagingAdapterRecyclerPoison.ViewHolderItemsPoison>(ResponsePoisonDiffItemCallback) , InterfaceRecycler {

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

 /*   override fun getItemCount(): Int {
        return listPoisons.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun clearList() {
        listPoisons = listOf()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setNewListPoison(listPoisons: List<Poison>) {
        this.listPoisons = listPoisons
        notifyDataSetChanged()
    }*/

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

        override fun bind(poison: Poison?) {
            poison?.let {
                itemClicked(poison)

                bindingViewHolder.header.text = poison.name
                bindingViewHolder.descriptionItem.text = poison.description
                bindingViewHolder.cardItems.elevation = CARD_ELEVATION

                Glide
                    .with(bindingViewHolder.imageItem.context)
                    .load(BASE_URL_API + poison.imageUri)
                    .placeholder(R.drawable.placeholder_not_found)
                    .into(bindingViewHolder.imageItem)

                Glide
                    .with(bindingViewHolder.imageItemBack.context)
                    .load(BASE_URL_API + poison.categories.imageUBackUrl)
                    .placeholder(R.drawable.placeholder_not_found)
                    .into(bindingViewHolder.imageItemBack)
            }
        }
    }

    interface InterfaceViewHolderNotes {
        fun itemClicked(poison: Poison)
        fun bind(poison: Poison?)
    }
}