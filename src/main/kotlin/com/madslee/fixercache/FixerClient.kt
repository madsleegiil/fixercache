package com.madslee.fixercache

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.URL

private val baseUrl = Environment.get("fixerBaseUrl")
private val accessKey = Environment.get("fixerAccessKey")

fun getLatestCurrencyRates(base: String): RateInformation {
    val url = URL("$baseUrl$accessKey&format=1&base=$base")
    val json = url.readText()
    return objectMapper.readValue(json)
}

private val objectMapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

