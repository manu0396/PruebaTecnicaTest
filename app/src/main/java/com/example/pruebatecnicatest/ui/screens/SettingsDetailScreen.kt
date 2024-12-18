package com.example.pruebatecnicatest.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pruebatecnicatest.R
import com.example.pruebatecnicatest.ui.components.BottomNavigationBar
import com.example.pruebatecnicatest.ui.components.QRReader
import com.example.pruebatecnicatest.ui.components.RequestCameraPermission
import com.example.pruebatecnicatest.ui.theme.PruebaTecnicaTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDetailScreen(navController: NavController) {
    var showQRReader by remember { mutableStateOf(false) }
    var hasCameraPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current

    PruebaTecnicaTestTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Settings") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            bottomBar = { BottomNavigationBar(navController) },
            contentWindowInsets = WindowInsets(16.dp)
        ) { paddingValues ->
            if (showQRReader) {
                if (hasCameraPermission) {
                    QRReader(
                        onQrCodeScanned = { qrCode ->
                            showQRReader = false
                            Toast.makeText(context, "Scanned: $qrCode", Toast.LENGTH_SHORT).show()
                        },
                        onBack = { showQRReader = false }
                    )
                } else {
                    RequestCameraPermission {
                        hasCameraPermission = true
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    // Align the "Settings Screen" text to the top-center
                    Text(
                        text = "Settings Screen",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )

                    // Align the QR icon button to the center
                    Button(
                        onClick = { showQRReader = true },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(200.dp), // Circular button
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_qr_code_24),
                            contentDescription = "qr_code_icon",
                            modifier = Modifier.size(180.dp))
                    }
                }
            }
        }
    }
}
