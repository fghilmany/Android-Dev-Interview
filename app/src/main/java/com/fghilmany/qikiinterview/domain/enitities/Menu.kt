package com.fghilmany.qikiinterview.domain.enitities

data class MenuItem(
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val isAvaibility: Boolean
)