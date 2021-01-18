package com.androiddevs.news.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegistrationViewModel : ViewModel() {

    val errorMessage = MutableLiveData<Boolean>()
    val startActivity = MutableLiveData<Boolean>()
    val startLoginActivity = MutableLiveData<Boolean>()

    private fun validateForm(
        username: String,
        email: String,
        password: String
    ): Boolean {
        if (TextUtils.isEmpty(username)) {
            return false
        }
        if (TextUtils.isEmpty(email)) {
            return false
        }
        if (TextUtils.isEmpty(password)) {
            return false
        }
        if (password.length < 6) {
            return false
        }
        return true
    }

    fun createUser(username: String, email: String, password: String) {
        if (!validateForm(username, email, password)) {
            return
        }
        val mAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = task.result?.user
                    currentUser?.let { addUsernameToUser(username, it) }
                } else {
                    Log.d(RegistrationViewModel::class.java.name, "showing error message")
                    errorMessage.value = true
                }
            }
    }

    private fun addUsernameToUser(username: String, user: FirebaseUser) {
        val userProfileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()
        user.updateProfile(userProfileChangeRequest)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(RegistrationViewModel::class.java.name, "starting news activity")
                    startActivity.value = true
                }
            }
    }

    fun startLoginActivity() {
        Log.d(RegistrationViewModel::class.java.name, "starting login activity")
        startLoginActivity.value = true
    }
}
