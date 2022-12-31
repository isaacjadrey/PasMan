package com.codingwithjadrey.pasman.util

interface StateListener {
    fun onSuccess(message: String)

    fun onError(message: String)
}