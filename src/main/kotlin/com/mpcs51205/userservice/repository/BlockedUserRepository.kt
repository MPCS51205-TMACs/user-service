package com.mpcs51205.userservice.repository

import com.mpcs51205.userservice.models.BlockedUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlockedUserRepository: JpaRepository<BlockedUser, String> {
    fun getBlockedUserByEmail(email: String): BlockedUser?
}