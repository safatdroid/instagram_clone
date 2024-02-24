package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Model.User
import com.example.myapplication.Model.post
import com.example.myapplication.R
import com.example.myapplication.Utils.FOLLOW
import com.example.myapplication.Utils.POST
import com.example.myapplication.adapter.FollowRvAdapter
import com.example.myapplication.adapter.PostAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomeFragment : Fragment() {
        private lateinit var binding : FragmentHomeBinding
        private var postList=ArrayList<post>()
    private lateinit var adapter: PostAdapter
    private var followList=ArrayList<User>()
    private lateinit var followRvAdapter: FollowRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater, container, false)
        adapter= PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager=LinearLayoutManager(requireContext())
        binding.postRv.adapter=adapter
        followRvAdapter= FollowRvAdapter(requireContext(),followList)
        binding.followRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.followRv.adapter=followRvAdapter
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.materialToolbar2)
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW).get()
            .addOnSuccessListener {
                var tempList=ArrayList<User>()
            for (i in it.documents) {
            var user:User=i.toObject<User>()!!
                tempList.add(user)
            }
                followList.addAll(tempList)
                followRvAdapter.notifyDataSetChanged()
        }
        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList = ArrayList<post>()
            postList.clear()
            for (i in it .documents){
                var post:post=i.toObject<post>()!!
                tempList.add(post)
            }
                postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

                }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
            }

