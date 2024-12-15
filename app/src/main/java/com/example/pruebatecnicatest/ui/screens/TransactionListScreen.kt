package com.example.pruebatecnicatest.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.pruebatecnicatest.ui.components.TransactionItem
import com.example.pruebatecnicatest.ui.viewmodel.TransactionViewModel

@Composable
fun TransactionListScreen(viewModel: TransactionViewModel) {
    val transactions by viewModel.transactions.collectAsState(emptyList())
    LazyColumn {
        items(transactions.size) { transaction ->
            TransactionItem(transactions[transaction])
        }
    }
}