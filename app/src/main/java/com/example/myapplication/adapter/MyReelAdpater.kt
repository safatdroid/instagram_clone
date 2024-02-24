package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.Model.reel
import com.example.myapplication.databinding.MyPostRvDesignBinding

class MyReelAdpater(var context: Context, var Reellist: ArrayList<reel>):
    RecyclerView.Adapter<MyReelAdpater.ViewHolder>()
{
    inner class ViewHolder(var binding: MyPostRvDesignBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReelAdpater.ViewHolder {
        var binding= MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

       Glide.with(context).load(Reellist.get(position).reelURL)
           .diskCacheStrategy(DiskCacheStrategy.ALL)
           .into(holder.binding.postImage)

    }

    override fun getItemCount(): Int {
        return Reellist.size
    }

}