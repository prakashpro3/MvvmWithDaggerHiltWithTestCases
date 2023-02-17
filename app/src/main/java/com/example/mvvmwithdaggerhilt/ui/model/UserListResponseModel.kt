package com.example.mvvmwithdaggerhilt.ui.model


data class UserListResponseModel(
    val `data`: List<Data>,
    val totalPages: Int,
    val totalPassengers: Int
)