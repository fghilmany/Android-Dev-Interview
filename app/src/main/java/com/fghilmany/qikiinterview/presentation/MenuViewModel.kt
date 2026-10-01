package com.fghilmany.qikiinterview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.fghilmany.qikiinterview.data.repository.MenuRepository
import com.fghilmany.qikiinterview.domain.enitities.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class UiState(
    val isLoading: Boolean = false,
    val items: List<MenuItem> = emptyList(),
    val cart: Map<String, Int> = emptyMap(),
    val error: String? = null,
    val isSubmitting: Boolean = false,
)

class MenuViewModel(private val repository: MenuRepository) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun load() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }
        try {
            val items = repository.getMenu()
            _state.update { it.copy(isLoading = false, items = items) }
        }catch (e: IOException){
            _state.update { it.copy(isLoading = false, error = e.message) }

        }
    }

    fun plus(id: Int) {
        _state.update {
            it.copy(
                cart = it.cart + (id.toString() to (it.cart[id.toString()] ?: 0) + 1)
            )
        }
    }

    fun minus(id: Int) {
        _state.update {
            it.copy(
                cart = it.cart + (id.toString() to (it.cart[id.toString()] ?: 0) - 1)
            )
        }
    }

    fun placeOrder() {
        if (_state.value.isSubmitting && _state.value.cart.isEmpty()) return
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, error = null) }
            try {
                repository.placeOrder(_state.value.cart)
                _state.update { it.copy(isSubmitting = false, cart = emptyMap()) }
            } catch (e: IOException){
                _state.update { it.copy(isSubmitting = false, error = e.message) }
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                MenuViewModel(MenuRepository())
            }
        }
    }

}

