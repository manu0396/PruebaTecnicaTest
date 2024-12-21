package com.example.pruebatecnicatest.utils

import android.app.Activity
import android.content.Context
import com.google.android.gms.wallet.*

const val LOAD_PAYMENT_DATA_REQUEST_CODE = 991

class GooglePayHelper(private val activity: Activity) {
    private val paymentsClient: PaymentsClient by lazy {
        Wallet.getPaymentsClient(
            activity,
            Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST).build()
        )
    }

    fun startGooglePayPayment(context: Context) {
        val activity = context as Activity
        val googlePayHelper = GooglePayHelper(activity)
        val paymentDataRequest = googlePayHelper.createPaymentDataRequest()
        val paymentsClient = googlePayHelper.createPaymentsClient()

        AutoResolveHelper.resolveTask(
            paymentsClient.loadPaymentData(paymentDataRequest),
            activity,
            LOAD_PAYMENT_DATA_REQUEST_CODE
        )
    }

    private fun createPaymentDataRequest(): PaymentDataRequest {
        val paymentRequestJson = PaymentsUtil.getPaymentDataRequest("10")
        return PaymentDataRequest.fromJson(paymentRequestJson.toString())
    }

    private fun createPaymentsClient(): PaymentsClient = paymentsClient
}
