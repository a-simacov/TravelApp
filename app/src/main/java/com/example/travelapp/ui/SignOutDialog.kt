package com.example.travelapp.ui

import android.app.AlertDialog
import android.content.Context
import com.example.travelapp.tools.AppUser
import com.example.travelapp.tools.Constants

class SignOutDialog(private val context: Context) {

    fun showAlert() {
        AlertDialog.Builder(context)
        .setTitle(Constants.DIALOG_TITLE_WARN)
        .setMessage(Constants.MSG_SIGN_OUT)
        .setPositiveButton(Constants.DIALOG_BTN_YES) { _, _ -> AppUser.signOut(context) }
        .setNegativeButton(Constants.DIALOG_BTN_NO, null)
        .show()
    }

}