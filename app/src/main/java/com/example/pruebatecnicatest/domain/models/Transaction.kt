package com.example.pruebatecnicatest.domain.models

data class Transaction(
    val id: String,
    val amount: Double,
    val description: String,
    val date: String
)