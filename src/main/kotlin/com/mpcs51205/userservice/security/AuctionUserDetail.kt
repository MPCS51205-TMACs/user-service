package com.mpcs51205.userservice.security

import com.mpcs51205.userservice.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuctionUserDetail(val user: User) : UserDetails {


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.user.getRoles().map { GrantedAuthority { "ROLE_$it" } }.toMutableList()
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return isEnabled
    }

    override fun isAccountNonLocked(): Boolean {
        return isEnabled
    }

    override fun isCredentialsNonExpired(): Boolean {
        return isEnabled
    }

    override fun isEnabled(): Boolean {
        return !user.suspended
    }
}