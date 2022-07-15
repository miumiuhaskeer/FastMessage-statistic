package com.miumiuhaskeer.fastmessage.statistic.repository

import com.miumiuhaskeer.fastmessage.statistic.extension._Criteria.andFieldBetweenEquals
import com.miumiuhaskeer.fastmessage.statistic.extension._Criteria.andFieldIs
import com.miumiuhaskeer.fastmessage.statistic.extension._Criteria.andFieldMatches
import com.miumiuhaskeer.fastmessage.statistic.model.entity.Message
import com.miumiuhaskeer.fastmessage.statistic.model.entity.MongoMessage
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindMessagesRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Repository
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Repository
class CustomMessageSearchRepositoryImpl(
    private val elasticsearchOperations: ElasticsearchOperations
): CustomMessageSearchRepository {

    /**
     * Find messages by filter
     */
    override fun findByFilter(filter: FindMessagesRequest): List<Message> {
        val criteria: Criteria = Criteria()
            .andFieldIs("document.chatId", filter.chatId)
            .andFieldIs("document.fromId", filter.fromId)
            .andFieldMatches("document.content", filter.content)
            .andFieldBetweenEquals("document.creationDateTime", filter.sendTimeStart, filter.sendTimeEnd)
        val searchQuery: Query = CriteriaQuery(criteria)
            .setPageable<Query>(PageRequest.of(filter.page!!, filter.size!!)).apply {
                addSourceFilter(FetchSourceFilter(arrayOf("document"), null))
            }

        val messages = elasticsearchOperations.search(
            searchQuery, MongoMessage::class.java, IndexCoordinates.of(MongoMessage.DOCUMENT_NAME)
        )

        // TODO change logic for return statement
        return messages.searchHits.map { it.content.document ?: Message() }
    }
}