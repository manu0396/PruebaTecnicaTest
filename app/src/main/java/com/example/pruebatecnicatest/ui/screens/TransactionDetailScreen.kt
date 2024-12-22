package com.example.pruebatecnicatest.ui.screens

import android.app.Activity
import android.os.Looper
import android.provider.Settings.Global
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pruebatecnicatest.R
import com.example.pruebatecnicatest.ui.animations.TripleOrbitLoadingAnimation
import com.example.pruebatecnicatest.ui.components.BottomNavigationBar
import com.example.pruebatecnicatest.ui.components.SimpleAlertDialog
import com.example.pruebatecnicatest.ui.viewmodel.TransactionViewModel
import com.example.pruebatecnicatest.utils.GooglePayHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Composable
fun TransactionDetailScreen(
    scope:CoroutineScope,
    navController: NavController,
    viewModel: TransactionViewModel,
    isSave: Boolean,
    id: String
) {
    val context = LocalContext.current
    val transactions by if(isSave) viewModel.localPosts.collectAsState() else viewModel.posts.collectAsState()
    val isLoading by viewModel.showLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val showError by viewModel.showError.collectAsState()


    val postDomain = transactions.find {
        it.id == id
    }

    val googlePayHelper = GooglePayHelper(context as Activity)

    Scaffold(
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
        bottomBar = {
            BottomNavigationBar(navController)
        },
        contentWindowInsets = WindowInsets(16.dp)
    ) { paddingValues ->
        if(isLoading){
            TripleOrbitLoadingAnimation(
                modifier = Modifier.fillMaxSize(0.5f)
            )
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(text = "Transaction ID: ${postDomain?.id}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Title: ${postDomain?.title}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Body: ${postDomain?.body}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "User ID: ${postDomain?.userId}", style = MaterialTheme.typography.bodyMedium)
            if(!isSave){
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp, bottom = 16.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                Looper.prepare()
                                if (postDomain != null) {
                                    googlePayHelper.startGooglePayPayment(context)
                                    // Simulate the payment is correctly until have an entrerprise account
                                    delay(10000L)
                                    viewModel.savePost(postDomain)
                                    scope.launch(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            "Payment successful. Post saved.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google_pay_logo),
                                contentDescription = stringResource(R.string.google_pay),
                                tint = Color.Unspecified,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.google_pay),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}