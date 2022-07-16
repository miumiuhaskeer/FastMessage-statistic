package com.miumiuhaskeer.fastmessage.statistic.properties.bundle

object ErrorBundle {

    private val bundle = SimpleBundle("static/error")

    operator fun get(key: String) = bundle[key]
    fun getFromText(text: String) = bundle.getFromText(text)
}