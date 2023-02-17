package com.example.mvvmwithdaggerhilt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmwithdaggerhilt.R
import com.example.mvvmwithdaggerhilt.ui.model.Data

class UserListAdapter(val context:Context, val userList:List<Data>):
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle:AppCompatTextView = itemView.findViewById(R.id.txtTitle)
        var txtSubTitle:AppCompatTextView = itemView.findViewById(R.id.txtSubTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(LayoutInflater.from(context).inflate(R.layout.user_list_view, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val userData = userList[position]

        holder.txtTitle.text = userData._id
        holder.txtSubTitle.text = userData.name

    }
}