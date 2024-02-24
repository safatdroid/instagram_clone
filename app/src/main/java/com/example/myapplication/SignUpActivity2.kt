package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.Model.User
import com.example.myapplication.Utils.USER_NODE
import com.example.myapplication.Utils.USER_PROFILE
import com.example.myapplication.Utils.uploadImage
import com.example.myapplication.databinding.ActivitySignUp2Binding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUpActivity2 : AppCompatActivity() {
    val binding by lazy {
        ActivitySignUp2Binding.inflate(layoutInflater)
    }
    lateinit var user: com.example.myapplication.Model.User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let {
                    uploadImage(uri, USER_PROFILE){
                        if (it!=null){

                        }
                    }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user=com.example.myapplication.Model.User()
        if (intent.hasExtra("MODE")){
            if (intent.getIntExtra("MODE",-1)==1){
                binding.signUpBtn.text="Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user= it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                        binding.name.editText?.setText(user.name)
                        binding.email.editText?.setText(user.name)
                        binding.password.editText?.setText(user.name)
                    }
            }
        }
        binding.signUpBtn.setOnClickListener {
            if (intent.hasExtra("MODE")){
                if (intent.getIntExtra("MODE",-1)==1){
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            startActivity(
                                Intent(
                                    this@SignUpActivity2,
                                    HomeActivity::class.java
                                )
                            )
                            finish()
                        }
                }
            }
            else {
                if (binding.name.editText?.text.toString().equals("") or
                    binding.email.editText?.text.toString().equals("") or
                    binding.password.editText?.text.toString().equals("")
                ) {
                    Toast.makeText(
                        this@SignUpActivity2,
                        "Please fill the all information",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString()
                    ).addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            user.name = binding.name.editText?.text.toString()
                            user.email = binding.email.editText?.text.toString()
                            user.password = binding.password.editText?.text.toString()
                            Firebase.firestore.collection(USER_NODE)
                                .document(Firebase.auth.currentUser!!.uid).set(user)
                                .addOnSuccessListener {
                                    startActivity(
                                        Intent(
                                            this@SignUpActivity2,
                                            HomeActivity::class.java
                                        )
                                    )
                                    finish()
                                }

                        } else {
                            Toast.makeText(
                                this@SignUpActivity2,
                                result.exception?.localizedMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }

        }
        binding.plus.setOnClickListener{
            launcher.launch("image/*")
        }

            binding.already.setOnClickListener {
                startActivity(Intent(this@SignUpActivity2,LoginActivity::class.java))
                finish()
            }


    }
}