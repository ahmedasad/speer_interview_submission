package com.example.speerinterviewsubmission.data.network

sealed class APIResponse<out T> {
    object Loading : APIResponse<Nothing>()
    data class Success<out T>(val data: T) : APIResponse<T>()
    data class Error<out T>(val message: String) : APIResponse<T>()
}