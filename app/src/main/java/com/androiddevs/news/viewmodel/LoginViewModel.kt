package com.androiddevs.news.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    val errorMessage = MutableLiveData<Boolean>()
    val startActivity = MutableLiveData<Boolean>()
    val startRegistrationActivity = MutableLiveData<Boolean>()

    fun signIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(LoginViewModel::class.java.name, "starting news activity")
                startActivity.value = true
            } else {
                Log.d(LoginViewModel::class.java.name, "showing error message")
                errorMessage.value = true
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            Log.d(LoginViewModel::class.java.name, "showing error message")
            errorMessage.value = true
            return false
        }
        if (password.isEmpty()) {
            Log.d(LoginViewModel::class.java.name, "showing error message")
            errorMessage.value = true
            return false
        }
        return true
    }

    fun startRegistrationActivity() {
        Log.d(LoginViewModel::class.java.name, "starting registration activity")
        startRegistrationActivity.value = true
    }
}
