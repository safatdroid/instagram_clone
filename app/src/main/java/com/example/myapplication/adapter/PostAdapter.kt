package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.User
import com.example.myapplication.Model.post
import com.example.myapplication.R
import com.example.myapplication.Utils.USER_NODE
import com.example.myapplication.databinding.PostRvBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostAdapter (var context : Context,var  postList: ArrayList<post>):RecyclerView.Adapter<PostAdapter.MyHolder>(){
    inner class MyHolder( var binding: PostRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding=PostRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).uid).get()
                .addOnSuccessListener {
                    var user = it.toObject<User>()
                    Glide.with(context).load(user!!.image).placeholder(R.drawable.profilepicture).into(holder.binding.profileImage)
                    holder.binding.name.text = user.name
                }
        }catch (e:Exception){

        }
    Glide.with(context).load(postList.get(position).postURL).placeholder(R.drawable.loading).into(holder.binding.imagePost)
        try {
            val text = TimeAgo.using(postList.get(position).time.toLong())
            holder.binding.time.text=text
        }catch (e:Exception){
            holder.binding.time.text=""
        }
        holder.binding.sendme.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type="text/plain"
            i.putExtra(Intent.EXTRA_TEXT , postList.get(position).postURL)
            context.startActivity(i)
        }
        holder.binding.csptoin.text=postList.get(position).caption
        holder.binding.likeme.setOnClickListener {
            holder.binding.likeme.setImageResource(R.drawable.heartred)
        }


    }
}