package com.example.travelapp.tools

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import java.text.SimpleDateFormat
import java.util.*

fun sendLocalBroadcastInfo(context: Context, action: String, info: String) {
    Intent().also {
        it.action = action
        it.putExtra("info", info)
        LocalBroadcastManager.getInstance(context).sendBroadcast(it)
    }
}

fun openSearch(context: Context, text: String?) {
    if (text.isNullOrBlank()) return

    val searchIntent = Intent(Intent.ACTION_WEB_SEARCH)
    searchIntent.putExtra(SearchManager.QUERY, text)
    context.startActivity(searchIntent)
}

fun openActivity(context: Context, cls: Class<*>) {
    context.startActivity(
        Intent(context, cls)
    )
}

fun showToast(context: Context, msg: String?) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

fun getUserNamePrefs(context: Context): String {
    val prefs = context.getSharedPreferences(
        Constants.PREFS_FILE_NAME,
        AppCompatActivity.MODE_PRIVATE
    )
    return prefs.getString(Constants.PREFS_USERNAME_KEY, "") ?: ""
}

fun getMinDate(dateString: String): String {
    val instAfterThreeDays = Calendar.getInstance().also { it.add(Calendar.DATE, 3) }
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).also { sdf ->
        val date = sdf.parse(dateString)
        val datePlus3Days = sdf.parse(sdf.format(instAfterThreeDays.time))
        return sdf.format(minOf(date, datePlus3Days))
    }
}

fun updateUserImg(context: Context, imageView: ImageView) {
    try {
        // Используется фейковый заголовок, т.к. домен png.pngtree.com без заголовка возвращает 403 ошибку
        val imgUrlWithFakeHeader = GlideUrl(
            AppUser.imgUrl, LazyHeaders.Builder()
                .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36"
                )
                .build()
        )
        Glide.with(context)
            .load(imgUrlWithFakeHeader)
            .error(com.google.android.material.R.drawable.abc_ic_clear_material)
            .into(imageView)
    } catch (e: Exception) {
        println(e.message)
    }
}
