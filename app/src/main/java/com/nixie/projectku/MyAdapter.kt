package com.nixie.projectku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: List<User>) : RecyclerView.Adapter<MyAdapter.UserViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class UserViewHolder(itemView: View, listener: OnItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.foto)
        val tvFullname: TextView = itemView.findViewById(R.id.txt_fullname)
        val tvEmail: TextView = itemView.findViewById(R.id.txt_email)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_listt, parent, false)
        return UserViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.tvFullname.text = user.fullname
        holder.tvEmail.text = user.email
        holder.imgPhoto.setImageResource(user.photoResId)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
