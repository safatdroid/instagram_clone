package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.reel
import com.example.myapplication.R
import com.example.myapplication.databinding.ReelDsBinding
import com.squareup.picasso.Picasso

class reelAapater (var context:Context,var reelList:ArrayList<reel>) : RecyclerView.Adapter<reelAapater.ViewHolder>() {
    inner class ViewHolder(var binding : ReelDsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDsBinding.inflate(LayoutInflater.from(context),parent,false)
             return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       Picasso.get().load(reelList.get(position).profileLink).placeholder(R.drawable.profile).into(holder.binding.profileImage)
        holder.binding.captionC.setText(reelList.get(position).caption)
        holder.binding.videoView.setVideoPath(reelList.get(position).reelURL)
        holder.binding.videoView.setOnPreparedListener {
            holder.binding.progressBar.visibility=View.GONE
            holder.binding.videoView.start()
        }
    }

}