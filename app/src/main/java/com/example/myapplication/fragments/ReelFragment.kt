package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.Model.reel
import com.example.myapplication.Utils.REEL
import com.example.myapplication.adapter.reelAapater
import com.example.myapplication.databinding.FragmentReelBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelFragment : Fragment() {
    private lateinit var binding:FragmentReelBinding
    lateinit var adapter:reelAapater
    var reelList=ArrayList<reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentReelBinding.inflate( inflater,container, false)
        adapter= reelAapater(requireContext(),reelList)
        binding.viewPager.adapter=adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList=ArrayList<reel>()
            reelList.clear()
            for (i in it.documents){
                var reel=i.toObject<reel>()!!
                tempList.add(reel)

            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {

            }
    }
