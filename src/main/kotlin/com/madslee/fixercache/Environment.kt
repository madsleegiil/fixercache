package com.madslee.fixercache

object Environment {
    private val envVariables: MutableMap<String, String> = HashMap()

    fun get(s: String): String {
        return envVariables[s] ?: System.getenv(s) ?: throw RuntimeException("env variable $s missing")
    }
    
    fun set(s: String, value: String) {
        envVariables[s] = value
    }
}