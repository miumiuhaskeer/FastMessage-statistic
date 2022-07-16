package com.miumiuhaskeer.fastmessage.statistic.model

import org.springframework.security.core.userdetails.UserDetails

abstract class ExtendedUserDetails: UserDetails {

    abstract val id: Long?
    abstract val email: String?

    override fun getUsername() = email
}