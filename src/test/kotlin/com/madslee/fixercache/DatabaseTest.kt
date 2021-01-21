package com.madslee.fixercache

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.io.File

class DatabaseTest {

    @AfterEach
    fun tearDown() {
        // Set databasePath in ENV relative to this location
        val databasePath = System.getenv("databasePath")
        File(databasePath).delete()
    }

    @Test
    fun `only one object per base currency can exist in database`() {
        val firstCurrencyRateInformation = CurrencyRateInformation("EUR", listOf(mapOf(Pair("USD", 12.2))))
        saveAndReplaceCurrencyRateInformation(firstCurrencyRateInformation)

        val firstSaved = getCurrencyRateInformation(firstCurrencyRateInformation.base)
        assertThat(firstSaved!!.base).isEqualTo(firstCurrencyRateInformation.base)
        assertThat(firstSaved.rates).containsExactly(firstCurrencyRateInformation.rates[0])

        val secondCurrencyRateInformation = CurrencyRateInformation("EUR", listOf(mapOf(Pair("NOK", 22.2))))
        saveAndReplaceCurrencyRateInformation(secondCurrencyRateInformation)

        val secondSaved = getCurrencyRateInformation(secondCurrencyRateInformation.base)
        assertThat(secondSaved!!.base).isEqualTo(secondCurrencyRateInformation.base)
        assertThat(secondSaved.rates).containsExactly(secondCurrencyRateInformation.rates[0])
    }
}