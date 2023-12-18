package com.example.sampleapp.common

sealed class State<out T> {
    data class Success<out T : Any>(val data: T) : State<T>()
    object Error : State<Nothing>()
    object Loading : State<Nothing>()
}
