package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.post
import com.example.myapplication.databinding.MyPostRvDesignBinding
import com.squareup.picasso.Picasso

class MyPostRvAdapter(var context: Context, var postList: ArrayList<post>):
    RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>()
{
    inner class ViewHolder(var binding: MyPostRvDesignBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostRvAdapter.ViewHolder {


       var binding=MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPostRvAdapter.ViewHolder, position: Int) {

        Picasso.get().load(postList.get(position).postURL).into(holder.binding.postImage)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}