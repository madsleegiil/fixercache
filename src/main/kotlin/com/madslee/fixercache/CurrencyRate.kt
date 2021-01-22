package com.madslee.fixercache

import org.slf4j.LoggerFactory
import java.io.Serializable
import java.time.LocalDateTime

object CurrencyRateCache {
    val logger = LoggerFactory.getLogger("CurrencyRateCache")
    val rateInformationValidForHours = 2L
    val rateMap = mutableMapOf<String, RateInformationTime>()

    fun getCurrencyRateInformation(
        baseCurrency: String,
        getCurrencyRates: (base: String) -> RateInformation
    ): RateInformation {
        val existingRateInformation = getNonOutdatedRateInformation(baseCurrency)
        if (existingRateInformation != null) return existingRateInformation

        logger.info("No recent currency rate information for $baseCurrency, will GET from external API")
        val rateInformation = getCurrencyRates(baseCurrency)
        rateMap[rateInformation.base] = RateInformationTime(LocalDateTime.now(), rateInformation)
        return rateInformation
    }

    private fun getNonOutdatedRateInformation(baseCurrency: String): RateInformation? {
        val currencyRateInformationTime = rateMap[baseCurrency] ?: return null

        return if (currencyRateInformationTime.dateTimeFetched.isBefore(
                LocalDateTime.now().minusHours(
                    rateInformationValidForHours
                )
            )
        ) null else currencyRateInformationTime.rateInformation
    }
}

data class RateInformationTime(
    val dateTimeFetched: LocalDateTime,
    val rateInformation: RateInformation
)

data class RateInformation(
    val base: String,
    val rates: List<Map<String, Double>>
) : Serializable
