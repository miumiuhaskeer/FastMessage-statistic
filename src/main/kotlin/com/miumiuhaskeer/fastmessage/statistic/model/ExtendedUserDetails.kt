package com.miumiuhaskeer.fastmessage.statistic.model

import org.springframework.security.core.userdetails.UserDetails

abstract class ExtendedUserDetails: UserDetails {

    abstract fun getId(): Long?
    abstract fun getEmail(): String?

    override fun getUsername() = getEmail()
}