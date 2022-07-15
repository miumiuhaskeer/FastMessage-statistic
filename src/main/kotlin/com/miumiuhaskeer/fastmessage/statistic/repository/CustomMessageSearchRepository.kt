package com.miumiuhaskeer.fastmessage.statistic.repository

import com.miumiuhaskeer.fastmessage.statistic.model.entity.Message
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindMessagesRequest

interface CustomMessageSearchRepository {
    fun findByFilter(filter: FindMessagesRequest): List<Message>
}