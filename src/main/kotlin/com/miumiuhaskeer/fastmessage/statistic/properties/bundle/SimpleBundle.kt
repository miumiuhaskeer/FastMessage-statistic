package com.miumiuhaskeer.fastmessage.statistic.properties.bundle

import java.util.*

/**
 * Simple bundle class that helps to get string resource
 *
 * @param baseName name or path to properties file without extension
 * @throws MissingResourceException if no resource bundle for the specified base name can be found
 */
class SimpleBundle(
    baseName: String
) {

    private val bundle: ResourceBundle = ResourceBundle.getBundle(baseName)

    /**
     * Get string property by key
     *
     * @param key string property key
     * @return string property
     * @throws MissingResourceException if no object for the given key can be found
     * @throws ClassCastException if the object found for the given key is not a string
     */
    operator fun get(key: String): String? = bundle.getString(key)

    /**
     * Get string resource from property key or return plain text
     *
     * @param text some plain text or property or is the key if surrounded by brackets (example: {key}}
     * @return string property or text parameter
     * @see SimpleBundle.get
     */
    fun getFromText(text: String) = if (text.startsWith("{") && text.endsWith("}")) {
        get(text.substring(1, text.length - 1))
    } else {
        text
    }
}