package com.miumiuhaskeer.fastmessage.statistic.repository

import com.miumiuhaskeer.fastmessage.statistic.extension._Criteria.andFieldBetweenEquals
import com.miumiuhaskeer.fastmessage.statistic.extension._Criteria.andFieldIs
import com.miumiuhaskeer.fastmessage.statistic.model.entity.UserInfo
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindUserInfoRequest
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomUserInfoRepositoryImpl(
    private val elasticsearchOperations: ElasticsearchOperations
): CustomUserInfoRepository {

    override fun findByFilter(filter: FindUserInfoRequest): List<UserInfo> {
        val criteria = Criteria()
            .andFieldIs("userId", filter.userId)
            .andFieldIs("email", filter.email)
            .andFieldBetweenEquals("creationDateTime", filter.creationDateTimeStart, filter.creationDateTimeEnd)
            .andFieldBetweenEquals("messageCount", filter.messageCountStart, filter.messageCountEnd)
            .andFieldBetweenEquals("chatCount", filter.chatCountStart, filter.chatCountEnd)
        val searchQuery: Query = CriteriaQuery(criteria)
            .setPageable(PageRequest.of(filter.page!!, filter.size!!))

        val userInfoSearchHits = elasticsearchOperations.search(
            searchQuery, UserInfo::class.java, IndexCoordinates.of(UserInfo.DOCUMENT_NAME)
        )

        return userInfoSearchHits.searchHits.map { it.content }
    }
}