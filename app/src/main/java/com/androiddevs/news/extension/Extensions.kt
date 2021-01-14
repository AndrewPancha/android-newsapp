package com.androiddevs.news.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.androiddevs.news.ui.LoginActivity
import com.androiddevs.news.ui.NewsActivity
import com.androiddevs.news.ui.RegistrationActivity

fun Context.displayToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.displayToast(message: String) {
    context?.displayToast(message)
}

fun ViewGroup.inflateView(layout: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layout, this, attachToRoot)

inline fun <T> LifecycleOwner.bind(
    liveData: LiveData<T>,
    crossinline onChanged: (T) -> Unit
) {
    liveData.observe(this) { onChanged.invoke(it) }
}

fun View.makeVisible(it: Boolean) {
    visibility = if (it) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Activity.startLoginActivity(bundle: Bundle = Bundle()) {
    startActivity(Intent(this, LoginActivity::class.java).putExtras(bundle))
}

fun Activity.startRegistrationActivity(bundle: Bundle = Bundle()) {
    startActivity(Intent(this, RegistrationActivity::class.java).putExtras(bundle))
}

fun Activity.startNewsActivity(bundle: Bundle = Bundle()) {
    startActivity(Intent(this, NewsActivity::class.java).putExtras(bundle))
}
