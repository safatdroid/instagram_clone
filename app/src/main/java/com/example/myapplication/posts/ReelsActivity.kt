package com.example.myapplication.posts

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.HomeActivity
import com.example.myapplication.Model.User
import com.example.myapplication.Model.reel
import com.example.myapplication.Utils.REEL
import com.example.myapplication.Utils.REEL_FOLDER
import com.example.myapplication.Utils.USER_NODE
import com.example.myapplication.Utils.uploadVideo
import com.example.myapplication.databinding.ActivityReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelsActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelsBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl: String
    lateinit  var progressDialog:ProgressDialog
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER, progressDialog) { url ->
                if (url != null) {
                    videoUrl = url
                }
            }
            }
        }
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)
            progressDialog=ProgressDialog(this)
            binding.uploadReels.setOnClickListener {
                launcher.launch("video/*")
            }
            binding.cancel.setOnClickListener {
                startActivity(Intent(this@ReelsActivity, HomeActivity::class.java))
                finish()
            }
            binding.postBtn.setOnClickListener {
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                    var user:User = it.toObject<User>()!!
                    val reel: reel= reel(videoUrl!!,binding.caption.editableText?.toString().toString(),user.image!!)
                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document().set(reel)
                            .addOnSuccessListener {
                                startActivity(Intent(this@ReelsActivity,HomeActivity::class.java))
                                finish()
                            }
                }


                }
            }
        }
    }



