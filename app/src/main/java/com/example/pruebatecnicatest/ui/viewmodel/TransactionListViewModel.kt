package com.example.pruebatecnicatest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicatest.data.remote.repository.PostRepository
import com.example.pruebatecnicatest.domain.mapper.MainMapper.toDomain
import com.example.pruebatecnicatest.domain.models.PostDomain
import com.example.pruebatecnicatest.domain.useCase.GetAllPostUseCase
import com.example.pruebatecnicatest.utils.WrapperResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getAllPostUseCase: GetAllPostUseCase
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<PostDomain>>(emptyList())
    val transactions: StateFlow<List<PostDomain>> = _transactions

    private val _showLoading = MutableStateFlow<Boolean>(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    private val _showError = MutableStateFlow<Boolean>(false)
    val showError: StateFlow<Boolean> = _showError

    private val _errorMessage = MutableStateFlow<String>("")
    val errorMessage: StateFlow<String> = _errorMessage
    init {
        fetchTransactions()
    }

    private fun fetchTransactions() {
        _showLoading.value = true
        viewModelScope.launch {
            when (val fetchedTransactions = getAllPostUseCase.getAllPost()) {
                is WrapperResponse.Success -> {
                    _transactions.value = fetchedTransactions.data ?: listOf()
                    _showLoading.value = false
                }

                is WrapperResponse.Error -> {
                    _showError.value = true
                    _errorMessage.value = fetchedTransactions.message ?: "Se ha producido un error"
                    _showLoading.value = false
                }
            }

        }
    }
}
