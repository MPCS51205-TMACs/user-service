package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.AuthRequest
import com.mpcs51205.userservice.models.User
import com.mpcs51205.userservice.models.UserUpdate
import com.mpcs51205.userservice.service.BlockedUserService
import com.mpcs51205.userservice.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/admin/user")
class AdminController(val userService: UserService, val blockedUserService: BlockedUserService) {
    // TODO: endpoint permissions
    @PutMapping("/{userId}/suspend")
    fun suspendUser(@PathVariable userId: UUID) = userService.updateUserStatus(userId, true)

    @PutMapping("/{userId}/unsuspend")
    fun unsuspendUser(@PathVariable userId: UUID) = userService.updateUserStatus(userId, false)

    @PostMapping("/{userId}/block")
    fun blockUser(@PathVariable userId: UUID) = userService.blockUser(userId)

    @DeleteMapping("/{email}/block")
    fun unblockUser(@PathVariable email: String) = userService.unblockUser(email)

}