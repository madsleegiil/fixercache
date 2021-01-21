package com.madslee.fixercache

import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL

private val baseUrl = "http://data.fixer.io/api/latest?access_key="
private val accessKey = System.getenv("fixerAccessKey") ?: throw RuntimeException("env variable fixerAccessKey missing")

fun getLatestCurrencyRates(base: String): CurrencyRateInformation {
    val url = URL("$baseUrl$accessKey&format=1&base=$base")
    val json = url.readText()
    return objectMapper.readValue(json)
}

