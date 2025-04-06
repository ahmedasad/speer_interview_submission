package com.example.speerinterviewsubmission.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speerinterviewsubmission.data.model.User
import com.example.speerinterviewsubmission.databinding.ListItemFollowerFollowingBinding
import com.squareup.picasso.Picasso

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {


    private var users = ArrayList<User>()

    class ViewHolder(binding: ListItemFollowerFollowingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val profilePic: ImageView = binding.imgProfilePic
        val userName: TextView = binding.txtProfileUserName

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ListItemFollowerFollowingBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false
        )


        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.userName.text = users.get(position).login
        Picasso.with(viewHolder.profilePic.context).load(users.get(position).avatarUrl).fit()
            .into(viewHolder.profilePic)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = users.size

    fun updateUsers(userList: ArrayList<User>) {
        this.users = userList

        this.notifyDataSetChanged()
    }

}