package com.androiddevs.news.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androiddevs.news.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        sign_up_link_text_view.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegistrationActivity::class.java
                )
            )
        })
        sign_in_button.setOnClickListener(View.OnClickListener {
            val email: String = email_edit_text.text.toString().trim()
            val password: String = password_edit_text.text.toString().trim()
            signIn(email, password)
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            startActivity(Intent(applicationContext, NewsActivity::class.java))
        }
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, NewsActivity::class.java))
                } else {
                    Toast.makeText(this@LoginActivity, "sign in problem", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validateForm(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            email_edit_text.error = "Required."
            return false
        }
        if (TextUtils.isEmpty(password)) {
            password_edit_text.error = "Required."
            return false
        }
        return true
    }

}