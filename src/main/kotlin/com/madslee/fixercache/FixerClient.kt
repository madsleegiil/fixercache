package com.madslee.fixercache

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL

private val baseUrl = "http://data.fixer.io/api/latest?access_key="

fun getLatestCurrencyRates(accessKey: String, base: String): CurrencyRateInformation {
    val url = URL("$baseUrl$accessKey&format=1&base=$base")
    val json = url.readText()
    print(json)

    val mapper = jacksonObjectMapper().apply {
        configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    return mapper.readValue(json)
}

