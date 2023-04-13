package com.example.searchpoison.ui.fragments.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.searchpoison.R
import com.example.searchpoison.databinding.ItemRecyclerPoisonBinding
import com.example.searchpoison.repository.dataSourse.BASE_URL_API
import com.example.searchpoison.repository.dataSourse.ResponsePoison
import com.example.searchpoison.ui.fragments.SearchPoisonFragment
import java.util.*

class RecyclerPoison(private var listPoisons: ArrayList<ResponsePoison>, private val fragment: SearchPoisonFragment) : RecyclerView.Adapter<RecyclerPoison.ViewHolderItemsPoison>() , InterfaceRecycler {

    companion object {
        private const val CARD_ELEVATION = 0f
    }

    private var _binding : ItemRecyclerPoisonBinding? = null
    private val binding : ItemRecyclerPoisonBinding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemsPoison {
        _binding = ItemRecyclerPoisonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderItemsPoison(binding.root,binding)
    }

    override fun onBindViewHolder(holder: ViewHolderItemsPoison, position: Int) {
        val poison = listPoisons[position]

        holder.itemClicked()

        holder.headerAuthor.text = poison.name
        holder.descriptionItems.text = poison.description

        Glide.with(holder.imageItems.context).load(BASE_URL_API + poison.imageUri).placeholder(R.drawable.placeholder_not_found).into(holder.imageItems)

        holder.cardItems.elevation = CARD_ELEVATION
    }

    override fun getItemCount(): Int {
        return listPoisons.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun clearListNews() {
        listPoisons.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList(listNews: ArrayList<ResponsePoison>) {
        this.listPoisons = listNews
        notifyDataSetChanged()
    }

    inner class ViewHolderItemsPoison(view: View, binding: ItemRecyclerPoisonBinding) : RecyclerView.ViewHolder(view), InterfaceViewHolderNotes {
        val headerAuthor = binding.header
        val descriptionItems = binding.descriptionItems
        val imageItems = binding.imageItems
        val cardItems = binding.cardItems

        override fun itemClicked() {
            cardItems.setOnClickListener {

            }
        }
    }

    interface InterfaceViewHolderNotes {
        fun itemClicked()
    }
}