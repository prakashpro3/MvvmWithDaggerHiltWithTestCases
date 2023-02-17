package com.example.mvvmwithdaggerhilt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmwithdaggerhilt.databinding.ErrorStateBinding
import javax.inject.Inject

class LoadingStateAdapter @Inject constructor(
    private val retry : ()-> Unit
):LoadStateAdapter<LoadingStateAdapter.LoadingViewHolder>() {

    class LoadingViewHolder(private val binding: ErrorStateBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.btnRetry.setOnClickListener{
                retry()
            }
        }
        fun bind(loadState: LoadState) {
            binding.apply {
                pbProgress.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                txtError.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingViewHolder {
        return LoadingViewHolder(ErrorStateBinding.inflate(LayoutInflater.from(parent.context), parent, false), retry)
    }
}