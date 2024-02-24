package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.auth.User

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.LoginBtn.setOnClickListener {
        if(binding.Email.editText?.text.toString().equals("") or
            binding.Password.editText?.text.toString().equals("")
            ) {
            Toast.makeText(this@LoginActivity, "Please Fill All The Details", Toast.LENGTH_SHORT).show()
            
        }else {
            var user=com.example.myapplication.Model.User(binding.Email.editText?.text.toString(),
            binding.Password.editText?.text.toString())
            Firebase.auth.signInWithEmailAndPassword(user.email!!, user.password!!)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                        finish()
                    }else {
                        Toast.makeText(this@LoginActivity,it.exception?.localizedMessage,
                            Toast.LENGTH_SHORT).show()
                    }

                }
        }
        }
    }
}