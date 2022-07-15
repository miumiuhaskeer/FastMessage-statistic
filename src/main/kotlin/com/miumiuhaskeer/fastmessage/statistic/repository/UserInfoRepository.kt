package com.miumiuhaskeer.fastmessage.statistic.repository

import com.miumiuhaskeer.fastmessage.statistic.model.entity.UserInfo
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserInfoRepository: ElasticsearchRepository<UserInfo, String>, CustomUserInfoRepository {
    fun findByUserId(userId: Long): Optional<UserInfo>
}