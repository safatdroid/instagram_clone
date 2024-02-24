package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Utils.USER_NODE
import com.example.myapplication.adapter.SearchAdapter
import com.example.myapplication.databinding.FragmentSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class SearchFragment : Fragment() {
        lateinit var binding : FragmentSearchBinding
        lateinit var adapter : SearchAdapter
        var userList=ArrayList<com.example.myapplication.Model.User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(inflater,container, false)
        binding.rv.layoutManager=LinearLayoutManager(requireContext())
        adapter=SearchAdapter(requireContext(),userList)
        binding.rv.adapter=adapter
        Firebase.firestore.collection(USER_NODE).get().addOnSuccessListener {
            var  tempList=ArrayList<com.example.myapplication.Model.User>()
            userList.clear()
            for (i in it.documents ) {
                if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())) {
                } else {
                    var user: User = i.toObject<User>()!!
                    tempList.add(com.example.myapplication.Model.User())
                }
            }
            userList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        binding.searchButton.setOnClickListener {
            var text = binding.searchView.text.toString()
            Firebase.firestore.collection(USER_NODE).whereEqualTo("name",text).get().addOnSuccessListener {

                var  tempList=ArrayList<com.example.myapplication.Model.User>()
                userList.clear()
            if (it.isEmpty) {

            }else{
                for (i in it.documents ) {
                    if (i.id.toString().equals(Firebase.auth.currentUser!!.uid.toString())) {
                    } else {
                        var user: User = i.toObject<User>()!!
                        tempList.add(com.example.myapplication.Model.User(  ))
                    }
                }
                userList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

            }

            }


        return binding.root
    }

    companion object {

    }
}

