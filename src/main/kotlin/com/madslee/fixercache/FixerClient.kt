package com.madslee.fixercache

import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL

private val baseUrl = Environment.get("fixerBaseUrl")
private val accessKey = Environment.get("fixerAccessKey")

fun getLatestCurrencyRates(base: String): CurrencyRateInformation {
    val url = URL("$baseUrl$accessKey&format=1&base=$base")
    val json = url.readText()
    return objectMapper.readValue(json)
}

