package com.miumiuhaskeer.fastmessage.statistic.repository

import com.miumiuhaskeer.fastmessage.statistic.model.entity.UserInfo
import com.miumiuhaskeer.fastmessage.statistic.model.request.FindUserInfoRequest

interface CustomUserInfoRepository {
    fun findByFilter(filter: FindUserInfoRequest): List<UserInfo>
}