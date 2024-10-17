package com.lordanti.quizgameapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.lordanti.quizgameapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loginBinding.signinButton.setOnClickListener {
            val userEmail = loginBinding.TextInputLoginEmail.text.toString()
            val userPassword = loginBinding.TextInputLoginPassword.text.toString()
            signinWithFirebase(userEmail, userPassword)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        loginBinding.textViewSignup.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        loginBinding.textViewForgotPassword.setOnClickListener{
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
    fun signinWithFirebase(userEmail : String, userPassword : String){
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Toast.makeText(
                    applicationContext,
                    "Welcome to Quiz Game",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                Toast.makeText(
                    applicationContext,
                    task.exception?.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null){
            Toast.makeText(
                applicationContext,
                "Welcome to Quiz Game",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}