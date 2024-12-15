package com.example.pruebatecnicatest.ui.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicatest.ui.components.TransactionItem
import com.example.pruebatecnicatest.ui.viewmodel.TransactionViewModel

@Composable
fun TransactionListScreen(viewModel: TransactionViewModel) {
    val transactions by viewModel.transactions.collectAsState(emptyList())
    val showLoading by viewModel.showLoading.collectAsState()
    val showError by viewModel.showError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    Scaffold (
        contentWindowInsets = WindowInsets(16.dp)
    ){ paddingValues ->
        LazyColumn (
            modifier = Modifier.padding(paddingValues)
        ){
            items(transactions.size) { transaction ->
                TransactionItem(transactions[transaction])
            }
        }
    }

}