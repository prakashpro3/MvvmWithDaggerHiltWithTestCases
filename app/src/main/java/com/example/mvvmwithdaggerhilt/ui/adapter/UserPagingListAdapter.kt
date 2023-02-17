package com.example.mvvmwithdaggerhilt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmwithdaggerhilt.databinding.UserListViewBinding
import com.example.mvvmwithdaggerhilt.ui.model.Data
import java.util.zip.Inflater
import javax.inject.Inject

class UserPagingListAdapter @Inject constructor() : PagingDataAdapter<Data, UserPagingListAdapter.UserPagingListViewHolder>(diffUtil) {

    class UserPagingListViewHolder(private val binding:UserListViewBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(data: Data){
            binding.apply {
                txtTitle.text = data.name
                txtSubTitle.text = data._id
            }
        }
    }

    override fun onBindViewHolder(holder: UserPagingListViewHolder, position: Int) {
       val data = getItem(position)
        data?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPagingListViewHolder {
        return UserPagingListViewHolder(UserListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    object diffUtil : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
}