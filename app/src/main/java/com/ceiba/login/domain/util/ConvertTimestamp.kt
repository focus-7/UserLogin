package com.ceiba.login.domain.util

import java.text.SimpleDateFormat
import java.util.*

object ConvertDate {
    fun getTimeFormat(): String {
        val date = Date(System.currentTimeMillis())
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm a", Locale.getDefault())
        return format.format(date)
    }
}