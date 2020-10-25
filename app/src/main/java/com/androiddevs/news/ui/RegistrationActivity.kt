package com.androiddevs.news.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androiddevs.news.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()

        sign_in_link_text_view.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        })

        register_button.setOnClickListener {
            val username: String = username_edit_text.text.toString().trim()
            val email: String = email_edit_text.text.toString().trim()
            val password: String = password_edit_text.text.toString().trim()

            if (validateForm(username, email, password)) {
                createUser(username, email, password)
            }
        }
    }

    private fun createUser(username: String, email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    val currentUser = task.result!!.user
                    currentUser?.let { addUsernameToUser(username, it) }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "register problem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun addUsernameToUser(username: String, user: FirebaseUser) {
        val userProfileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()
        user.updateProfile(userProfileChangeRequest)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, NewsActivity::class.java))
                }
            }
    }

    private fun validateForm(
        username: String,
        email: String,
        password: String
    ): Boolean {
        if (TextUtils.isEmpty(username)) {
            username_edit_text.error = "Required."
            return false
        }
        if (TextUtils.isEmpty(email)) {
            email_edit_text.error = "Required."
            return false
        }
        if (TextUtils.isEmpty(password)) {
            password_edit_text.error = "Required."
            return false
        }
        if (password.length < 6) {
            password_edit_text.error = "Password must be at least 6 characters"
            return false
        }
        return true
    }
}