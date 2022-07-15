package com.miumiuhaskeer.fastmessage.statistic.service

import com.miumiuhaskeer.fastmessage.statistic.model.entity.Message
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindMessagesRequest
import com.miumiuhaskeer.fastmessage.statistic.repository.MessageSearchRepository
import org.springframework.stereotype.Service

@Service
class MessageSearchServiceImpl(
    private val messageSearchRepository: MessageSearchRepository
): MessageSearchService {

    override fun findByFilter(filter: FindMessagesRequest): List<Message> = messageSearchRepository.findByFilter(filter)
}