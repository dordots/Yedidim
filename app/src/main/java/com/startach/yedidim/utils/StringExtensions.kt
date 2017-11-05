package com.startach.yedidim.utils

class StringExtensions {
    companion object {
        val EMPTY_STRING = ""
    }
}

val String.Companion.empty: String?
    get() {
        return StringExtensions.EMPTY_STRING
    }