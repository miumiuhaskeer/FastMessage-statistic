package com.miumiuhaskeer.fastmessage.statistic.service

import com.miumiuhaskeer.fastmessage.statistic.model.entity.Message
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindMessagesRequest

interface MessageSearchService {
    fun findByFilter(filter: FindMessagesRequest): List<Message>
}