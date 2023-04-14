package com.example.searchpoison.ui.fragments.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchpoison.R
import com.example.searchpoison.databinding.ItemRecyclerPoisonBinding
import com.example.searchpoison.repository.dataSourse.BASE_URL_API
import com.example.searchpoison.repository.dataSourse.Poison
import com.example.searchpoison.ui.fragments.SearchPoisonFragment
import java.util.*

class RecyclerPoison(private var listPoisons: List<Poison>, private val callbackItem: CallbackItem) : RecyclerView.Adapter<RecyclerPoison.ViewHolderItemsPoison>() , InterfaceRecycler {

    companion object {
        private const val CARD_ELEVATION = 0f
    }

    private var _binding : ItemRecyclerPoisonBinding? = null
    private val binding : ItemRecyclerPoisonBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemsPoison {
        _binding = ItemRecyclerPoisonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderItemsPoison(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderItemsPoison, position: Int) {
        val poison = listPoisons[position]

        holder.bin(poison)
        holder.itemClicked()
    }

    override fun getItemCount(): Int {
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
    }

    inner class ViewHolderItemsPoison(private val bindingViewHolder: ItemRecyclerPoisonBinding) : RecyclerView.ViewHolder(binding.root), InterfaceViewHolderNotes {

        override fun itemClicked() {
            bindingViewHolder.cardItems.setOnClickListener {
                callbackItem.itemClicked(listPoisons[layoutPosition].id)
            }
        }

        override fun bin(poison: Poison) {
            bindingViewHolder.header.text = poison.name
            bindingViewHolder.descriptionItems.text = poison.description
            bindingViewHolder.cardItems.elevation = CARD_ELEVATION

            Glide
                .with(bindingViewHolder.imageItems.context)
                .load(BASE_URL_API + poison.imageUri)
                .placeholder(R.drawable.placeholder_not_found)
                .into(bindingViewHolder.imageItems)
        }
    }

    interface InterfaceViewHolderNotes {
        fun itemClicked()
        fun bin(poison: Poison)
    }
}