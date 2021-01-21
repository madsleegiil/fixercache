package com.madslee.fixercache

import java.io.Serializable


data class CurrencyRateInformation(
    val base: String,
    val rates: List<Map<String, Double>>
) : Serializable

