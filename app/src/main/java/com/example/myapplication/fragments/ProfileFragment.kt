package com.example.myapplication.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.Model.User
import com.example.myapplication.R
import com.example.myapplication.SignUpActivity2
import com.example.myapplication.Utils.USER_NODE
import com.example.myapplication.adapter.ViewPagerAdapter
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

private lateinit var binding: FragmentProfileBinding
private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater ,container, false)

        binding.editProfile.setOnClickListener {
            val intent=Intent(activity,SignUpActivity2::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()
        }
        viewPagerAdapter=ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragment(MyPostsFragment(),"My Post")
        viewPagerAdapter.addFragment(MyReelsFragment(),"My Reels")
        binding.viewPager.adapter=viewPagerAdapter
        binding.tableLayout.setupWithViewPager(binding.viewPager)
        return binding.root
    }

companion object{

}

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User= it.toObject<User>()!!
                binding.name.text=user.name
                binding.bio.text=user.email
                if (!user.image.isNullOrEmpty()){
                        Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
    }
