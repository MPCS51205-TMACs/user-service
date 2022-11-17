package com.mpcs51205.userservice.service

import com.mpcs51205.userservice.models.BlockedUser
import com.mpcs51205.userservice.repository.BlockedUserRepository
import org.springframework.stereotype.Service

@Service
class BlockedUserService(val blockedUserRepository: BlockedUserRepository) {
    fun isBlocked(email: String) = blockedUserRepository.getBlockedUserByEmail(email) != null

    fun block(email: String): BlockedUser {
        val blockedUser = BlockedUser()
        blockedUser.email = email
        return blockedUserRepository.save(blockedUser)
    }

    fun unblock(email: String) {
        val blockedUser = blockedUserRepository.getReferenceById(email)
        blockedUserRepository.delete(blockedUser)
    }

}