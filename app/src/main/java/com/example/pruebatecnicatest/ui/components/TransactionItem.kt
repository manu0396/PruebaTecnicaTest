package com.example.pruebatecnicatest.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicatest.domain.models.Transaction

@Composable
fun TransactionItem (transaction: Transaction){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = transaction.description, style = MaterialTheme.typography.bodySmall)
        Text(text = "${transaction.amount}", style = MaterialTheme.typography.labelSmall)
        Text(text = transaction.date, style = MaterialTheme.typography.labelMedium)
    }
}