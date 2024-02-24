package com.example.myapplication.posts


import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.HomeActivity
import com.example.myapplication.Model.User
import com.example.myapplication.Model.post
import com.example.myapplication.Utils.POST
import com.example.myapplication.Utils.POST_FOLDER
import com.example.myapplication.Utils.USER_NODE
import com.example.myapplication.Utils.uploadImage
import com.example.myapplication.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String?=null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {
            uploadImage(uri, POST_FOLDER){
                    url ->
                if (url!=null){
                        binding.selectImage.setImageURI(uri)
                    imageUrl=url
                }

            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }
       binding.selectImage.setOnClickListener {
           launcher.launch("image/*")
       }
        binding.cancel.setOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }
        binding.postBtn.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document().get()
                .addOnSuccessListener {
                var user=it.toObject<User>()!!

            val post: post = post(
                postURL = imageUrl!!,
                caption = binding.caption.editableText?.toString().toString(),
                uid = Firebase.auth.currentUser!!.uid,
                time=System.currentTimeMillis())

            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post)
                    .addOnSuccessListener {
                        startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                        finish()
                    }

            }
                }
        }
    }
}