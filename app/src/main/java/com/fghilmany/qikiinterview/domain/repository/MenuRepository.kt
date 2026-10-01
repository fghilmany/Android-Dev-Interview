package com.fghilmany.qikiinterview.domain.repository

import com.fghilmany.qikiinterview.domain.enitities.MenuItem

interface MenuRepository {
    suspend fun getMenu(): List<MenuItem>
    suspend fun placeOrder(items: Map<String, Int>): String
}