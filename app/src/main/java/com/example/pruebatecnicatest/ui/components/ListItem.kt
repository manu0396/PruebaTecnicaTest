package com.example.pruebatecnicatest.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pruebatecnicatest.domain.models.PostDomain
import com.example.pruebatecnicatest.ui.theme.PurpleGrey80

@Composable
fun TransactionItem (
    modifier: Modifier,
    postDomain: PostDomain
){
    Card (
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.cardColors(
            PurpleGrey80
        ),
        shape = RoundedCornerShape(10),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        border = BorderStroke(1.dp, Color.Black)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = postDomain.id, style = MaterialTheme.typography.bodySmall)
            Text(text = postDomain.body, style = MaterialTheme.typography.labelSmall)
            Text(text = postDomain.title, style = MaterialTheme.typography.labelMedium)
            Text(text = postDomain.userId, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    // Sample PostDomain object for preview
    val samplePost = PostDomain(
        id = "1",
        body = "This is a sample body for the post.",
        title = "Sample Title",
        userId = "123"
    )
    TransactionItem(
        modifier = Modifier.padding(16.dp),
        postDomain = samplePost
    )
}