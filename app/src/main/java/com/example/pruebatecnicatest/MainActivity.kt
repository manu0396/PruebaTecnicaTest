package com.example.pruebatecnicatest

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.pruebatecnicatest.ui.nav.AppNavigator
import com.example.pruebatecnicatest.ui.theme.PruebaTecnicaTestTheme
import com.example.pruebatecnicatest.ui.viewmodel.TransactionViewModel
import com.example.pruebatecnicatest.utils.LOAD_PAYMENT_DATA_REQUEST_CODE
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: TransactionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PruebaTecnicaTestTheme {
                AppNavigator(lifecycleScope)
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val paymentData = data?.let { PaymentData.getFromIntent(it) }
                        val paymentToken = paymentData?.paymentMethodToken?.token
                        if (paymentToken != null) {
                            viewModel.savePostAfterPayment(viewModel.localPost.value, true)
                            Toast.makeText(this, "Payment successful. Post saved.", Toast.LENGTH_LONG).show()
                        } else {
                            viewModel.savePostAfterPayment(viewModel.localPost.value, false)
                            Toast.makeText(this, "Payment failed.", Toast.LENGTH_LONG).show()
                        }
                    }
                    RESULT_CANCELED -> {
                        viewModel.savePostAfterPayment(viewModel.localPost.value, false)
                        Toast.makeText(this, "Payment canceled.", Toast.LENGTH_LONG).show()
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                        viewModel.savePostAfterPayment(viewModel.localPost.value, false)
                        Toast.makeText(this, "Payment error.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
