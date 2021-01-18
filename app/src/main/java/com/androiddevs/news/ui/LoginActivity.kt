package com.androiddevs.news.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.news.R
import com.androiddevs.news.extension.bind
import com.androiddevs.news.extension.displayToast
import com.androiddevs.news.extension.startNewsActivity
import com.androiddevs.news.extension.startRegistrationActivity
import com.androiddevs.news.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindViewModel()
        setupUI()
    }

    private fun bindViewModel() {
        with(viewModel) {
            bind(errorMessage) {
                if (it) displayToast(getString(R.string.auth_error))
            }
            bind(startActivity) {
                if (it) {
                    startNewsActivity()
                }
            }
            bind(startRegistrationActivity) {
                if (it) {
                    this@LoginActivity.startRegistrationActivity()
                }
            }
        }
    }

    private fun setupUI() {
        sign_up_link_text_view.setOnClickListener {
            viewModel.startRegistrationActivity()
        }
        sign_in_button.setOnClickListener {
            val email: String = email_edit_text.text.toString().trim()
            val password: String = password_edit_text.text.toString().trim()
            viewModel.signIn(email, password)
        }
    }

    override fun onStart() {
        super.onStart()
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startNewsActivity()
        }
    }
}
