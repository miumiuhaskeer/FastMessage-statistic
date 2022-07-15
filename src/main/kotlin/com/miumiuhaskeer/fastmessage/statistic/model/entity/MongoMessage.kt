package com.miumiuhaskeer.fastmessage.statistic.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.miumiuhaskeer.fastmessage.statistic.model.entity.MongoMessage.Companion.DOCUMENT_NAME
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = DOCUMENT_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
class MongoMessage {

    companion object {
        const val DOCUMENT_NAME = "mongo_message"
    }

    @Id
    var id: String? = null

    @Field(name = "document", type = FieldType.Nested)
    var document: Message? = null
}