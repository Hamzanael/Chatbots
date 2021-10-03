package com.telelogx.chatbot.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserSecurityDetails(
    private val userName: String,
    private val password: String,
    private val permissions: String,
    private val enabled: Boolean
) : UserDetails {

    override fun getAuthorities(): List<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_$permissions"))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean { //todo check for expiration
        return true
    }

    override fun isEnabled(): Boolean {
        return enabled
    }


}