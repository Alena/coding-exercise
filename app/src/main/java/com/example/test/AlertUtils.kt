package com.example.test

import android.app.AlertDialog
import android.content.Context
import com.example.test.R

fun Context.showNetworkDialogAlert(message: Int): AlertDialog {
    return AlertDialog.Builder(this).run {
        setTitle(R.string.network_status)
            .setCancelable(true)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }

        create().apply {
            setCancelable(false)
            show()
        }
    }
}

fun Context.showDialogAlert(message: Int): AlertDialog {
    return AlertDialog.Builder(this).run {
        setCancelable(true)
            .setMessage(message)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.cancel() }

        create().apply {
            setCancelable(false)
            show()
        }
    }
}