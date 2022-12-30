package com.codingwithjadrey.pasman.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codingwithjadrey.pasman.data.entity.Pas
import com.codingwithjadrey.pasman.databinding.PasswordItemBinding

class PasswordAdapter : ListAdapter<Pas, PasswordAdapter.PasViewHolder>(DiffCallBack) {
    class PasViewHolder(private val binding: PasswordItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pas: Pas) {
            binding.pas = pas
            binding.executePendingBindings()
        }
    }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Pas>() {
            override fun areItemsTheSame(oldItem: Pas, newItem: Pas): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Pas, newItem: Pas): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasViewHolder {
        return PasViewHolder(
            PasswordItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PasViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}