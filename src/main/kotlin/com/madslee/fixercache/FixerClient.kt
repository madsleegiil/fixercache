package com.madslee.fixercache

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.http.BadGatewayResponse
import java.io.Serializable
import java.net.URL

private val baseUrl = Environment.get("fixerBaseUrl")
private val accessKey = Environment.get("fixerAccessKey")

fun getLatestCurrencyRates(base: String): RateInformation {
    try {
        val url = URL("$baseUrl$accessKey&format=1&base=$base")
        val json = url.readText()
        return objectMapper.readValue(json)
    } catch (e: Exception) {
        throw BadGatewayResponse("Error fetching rates from fixer.io for $base")
    }
}

private val objectMapper = jacksonObjectMapper().apply {
    configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}

data class RateInformation(
    val base: String,
    val rates: List<Map<String, Double>>
) : Serializable
