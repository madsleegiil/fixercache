package com.madslee.fixercache

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get

fun main() {
    Environment.set("fixerBaseUrl", "http://data.fixer.io/api/latest?access_key=")

    Javalin.create().start(7000).routes {
        get(":baseCurrency") { context ->
            val baseCurrency = context.pathParam("baseCurrency")
            val currencyRate = CurrencyRateCache.getCurrencyRateInformation(baseCurrency, ::getLatestCurrencyRates)
            context.json(currencyRate)
        }
    }
}