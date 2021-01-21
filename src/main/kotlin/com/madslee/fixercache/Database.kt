package com.madslee.fixercache

import com.fasterxml.jackson.module.kotlin.readValue
import org.dizitart.no2.Document
import org.dizitart.no2.Nitrite
import org.dizitart.no2.filters.Filters.eq

private val databasePath = System.getenv("databasePath") ?: throw RuntimeException("env variable databasePath missing")
private val database: Nitrite = Nitrite.builder()
    .compressed()
    .filePath(databasePath)
    .openOrCreate()
private val currencyCollection = database.getCollection("currencies")
private val identifierKey = "baseCurrency"
private val contentKey = "jsonString"

fun saveAndReplaceCurrencyRateInformation(currencyRateInformation: CurrencyRateInformation) {
    deleteCurrencyRateInformation(currencyRateInformation.base)
    currencyCollection.insert(toDocument(currencyRateInformation))
}

fun getCurrencyRateInformation(baseCurrency: String): CurrencyRateInformation? {
    val document = currencyCollection.find(eq(identifierKey, baseCurrency)).firstOrNull()
    return if (document != null) fromDocument(document) else null
}

fun deleteCurrencyRateInformation(baseCurrency: String) {
    currencyCollection.remove(eq(identifierKey, baseCurrency))
}

fun toDocument(currencyRateInformation: CurrencyRateInformation) =
    Document.createDocument(identifierKey, currencyRateInformation.base).apply {
        put(contentKey, objectMapper.writeValueAsString(currencyRateInformation))
    }

fun fromDocument(document: Document): CurrencyRateInformation = objectMapper.readValue(document[contentKey].toString())