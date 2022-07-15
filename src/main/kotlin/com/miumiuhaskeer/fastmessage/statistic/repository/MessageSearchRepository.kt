package com.miumiuhaskeer.fastmessage.statistic.repository

import com.miumiuhaskeer.fastmessage.statistic.model.entity.MongoMessage
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageSearchRepository: ElasticsearchRepository<MongoMessage, String>, CustomMessageSearchRepository