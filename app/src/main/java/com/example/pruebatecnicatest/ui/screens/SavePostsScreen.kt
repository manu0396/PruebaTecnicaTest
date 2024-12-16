package com.example.pruebatecnicatest.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pruebatecnicatest.R
import com.example.pruebatecnicatest.ui.animations.SimpleAlertDialog
import com.example.pruebatecnicatest.ui.animations.TripleOrbitLoadingAnimation
import com.example.pruebatecnicatest.ui.components.BottomNavigationBar
import com.example.pruebatecnicatest.ui.components.TransactionItem
import com.example.pruebatecnicatest.ui.viewmodel.TransactionViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SavePostsScreen (
    viewModel: TransactionViewModel,
    navController: NavController
){

    val context = LocalContext.current
    val transactions by viewModel.localPosts.collectAsState(emptyList())
    val showLoading by viewModel.showLoading.collectAsState()
    val showError by viewModel.showError.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(transactions) {
        viewModel.getAllLocalPost()
    }

    Scaffold(
        contentWindowInsets = WindowInsets(16.dp),
        topBar = {
            TopAppBar(
                title = { Text("Transaction Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        if (showLoading) {
            TripleOrbitLoadingAnimation(modifier = Modifier.fillMaxSize())
        }

        SimpleAlertDialog(
            context = context,
            show = showError,
            title = stringResource(R.string.error),
            text = errorMessage,
            onConfirm = viewModel::onConfirm,
            onDismiss = viewModel::onDismiss,
            elevation = 10
        )

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(transactions.size) { transaction ->
                TransactionItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {},
                            onLongClick = { navController.navigate("transactions/${transactions[transaction].id.toInt()}") }
                        ),
                    postDomain = transactions[transaction]
                )
            }
        }
    }
}