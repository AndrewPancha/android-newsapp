package com.androiddevs.news.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.news.R
import com.androiddevs.news.extension.*
import com.androiddevs.news.viewmodel.RegistrationViewModel
import kotlinx.android.synthetic.main.activity_registration.*


class RegistrationActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        bindViewModel()
        setupUI()
    }

    private fun bindViewModel() {
        with(viewModel) {
            bind(errorMessage) {
                if (it)
                    displayToast(getString(R.string.auth_error))
            }
            bind(startActivity) {
                if (it) {
                    startNewsActivity()
                }
            }
            bind(startLoginActivity) {
                if (it) {
                    this@RegistrationActivity.startLoginActivity()
                }
            }
        }
    }

    private fun setupUI() {
        sign_in_link_text_view.setOnClickListener {
            viewModel.startLoginActivity()
        }
        register_button.setOnClickListener {
            val username: String = username_edit_text.text.toString().trim()
            val email: String = email_edit_text.text.toString().trim()
            val password: String = password_edit_text.text.toString().trim()
            viewModel.createUser(username, email, password)
        }
    }
}
