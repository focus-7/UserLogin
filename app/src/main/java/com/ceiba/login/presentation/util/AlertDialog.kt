package com.ceiba.login.presentation.util

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StyleRes
import com.ceiba.login.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.alert(
    @StyleRes style: Int = 0,
    dialogBuilder: MaterialAlertDialogBuilder.() -> Unit,
) {
    MaterialAlertDialogBuilder(this, style)
        .apply {
            setCancelable(false)
            dialogBuilder()
            create()
            show()
        }
}

fun MaterialAlertDialogBuilder.positiveButton(
    text: String = context.resources.getString(R.string.accept),
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() },
) {
    this.setPositiveButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}

fun MaterialAlertDialogBuilder.negativeButton(
    text: String = context.resources.getString(R.string.cancel),
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() },
) {
    this.setNegativeButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}

fun MaterialAlertDialogBuilder.neutralButton(
    text: String = context.resources.getString(R.string.accept),
    handleClick: (dialogInterface: DialogInterface) -> Unit = { it.dismiss() },
) {
    this.setNeutralButton(text) { dialogInterface, _ -> handleClick(dialogInterface) }
}
