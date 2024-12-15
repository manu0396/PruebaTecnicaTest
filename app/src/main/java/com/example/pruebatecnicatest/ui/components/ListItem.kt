package com.example.pruebatecnicatest.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicatest.domain.models.PostDomain

@Composable
fun TransactionItem (postDomain: PostDomain){
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = postDomain.id, style = MaterialTheme.typography.bodySmall)
        Text(text = postDomain.body, style = MaterialTheme.typography.labelSmall)
        Text(text = postDomain.title, style = MaterialTheme.typography.labelMedium)
        Text(text = postDomain.userId, style = MaterialTheme.typography.labelMedium)
    }
}