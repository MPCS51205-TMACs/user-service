package com.mpcs51205.userservice.service

import com.mpcs51205.userservice.event.RabbitPublisher
import com.mpcs51205.userservice.exception.*
import com.mpcs51205.userservice.models.AuthRequest
import com.mpcs51205.userservice.models.User
import com.mpcs51205.userservice.models.UserUpdate
import com.mpcs51205.userservice.repository.UserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
    val blockedUserService: BlockedUserService,
    val rabbitPublisher: RabbitPublisher
) {
    fun getUserById(userId: UUID): User = userRepository.findByIdOrNull(userId) ?: throw ResourceDoesNotExistException()

    fun createUser(user: User): User {
        if (blockedUserService.isBlocked(user.email)) {
            throw BlockedUserException(user.email)
        }
        saveUser(user)
        rabbitPublisher.sendCreateEvent(user)
        return user
    }

    fun saveUser(user: User) {
        try {
            userRepository.save(user)
        } catch (e: DataIntegrityViolationException) {
            throw EmailAlreadyExists()
        }
    }

    fun deleteUser(userId: UUID) = userRepository.delete(getUserReference(userId))

    fun blockUser(userId: UUID) {
        val toBlock = getUserReference(userId)
        blockedUserService.block(toBlock.email)
        userRepository.delete(toBlock)
    }

    fun unblockUser(email: String) {
        blockedUserService.unblock(email)
    }

    fun updateUserStatus(userId: UUID, suspended: Boolean) {
        val userUpdate = UserUpdate()
        userUpdate.isSuspended = suspended
        updateUser(userUpdate, userId)
    }

    fun updateUser(updateSrc: UserUpdate, targetId: UUID): User {
        val target: User = getUserReference(targetId)
        val updateEvent = updateSrc.update(user = target)
        saveUser(user = target)
        rabbitPublisher.sendUpdateEvent(userUpdateEvent = updateEvent)
        return target
    }

    fun getUserByAuthRequest(authRequest: AuthRequest): User {
        val user: User =
            userRepository.getUserByEmail(authRequest.email) ?: throw ResourceDoesNotExistException()
        if (user.suspended) throw AccountSuspendedException()
        if (user.password == authRequest.password) {
            return user
        }
        throw UnauthorizedException()
    }

    private fun getUserReference(userId: UUID): User = userRepository.getReferenceById(userId)

}