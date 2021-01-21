package com.madslee.fixercache

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FixerClientTest {

    @Test
    fun `get latest currency rates from base`() {
        val base = "EUR"
        val currencyRateInformation = getLatestCurrencyRates(base)
        assertThat(currencyRateInformation).isNotNull
        assertThat(currencyRateInformation.rates).isNotEmpty
    }
}