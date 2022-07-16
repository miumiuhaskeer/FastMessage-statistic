package com.miumiuhaskeer.fastmessage.statistic.properties.bundle

object ErrorBundle {

    private val bundle = SimpleBundle("static/error")

    // TODO remove annotation after migrate AuthenticationErrorHandler to Kotlin
    @JvmStatic
    fun get(key: String) = bundle[key]
    fun getFromText(text: String) = bundle.getFromText(text)
}