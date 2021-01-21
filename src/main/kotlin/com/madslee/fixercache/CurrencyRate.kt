package com.madslee.fixercache


data class CurrencyRateInformation(
    val base: String,
    val date: String,
    val rates: List<Map<String, Double>>
)

