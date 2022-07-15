package com.miumiuhaskeer.fastmessage.statistic.extension

import org.springframework.data.elasticsearch.core.query.Criteria

object _Criteria {

    fun Criteria.andFieldIs(name: String, any: Any?) = any?.let {
        and(name).`is`(any)
    } ?: this

    fun Criteria.andFieldMatches(name: String, any: Any?) = any?.let {
        and(name).matches(any)
    } ?: this

    fun Criteria.andFieldBetweenEquals(name: String, start: Any?, end: Any?): Criteria {
        if (start == null && end == null) {
            return this
        }

        return and(name).apply {
            start?.let {
                greaterThanEqual(start)
            }

            end?.let {
                lessThanEqual(end)
            }
        }
    }
}