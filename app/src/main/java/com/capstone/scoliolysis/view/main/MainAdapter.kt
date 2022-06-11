package com.capstone.scoliolysis.view.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scoliolysis.databinding.ItemListBinding
import com.capstone.scoliolysis.model.DataItem

class MainAdapter(private val data: List<DataItem>) : RecyclerView.Adapter<MainAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val list = data[position]

        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(list.image)
                .into(imgItem)

            dateTV.text = list.createdAt
            descTV.text = "Nama: " + list.name + "\n \n Usia: " + list.dateOfBirth
            noticeTV.text = list.detection

            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data[holder.adapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }

    override fun getItemCount() = data.size

}