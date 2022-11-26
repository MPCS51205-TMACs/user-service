package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.User
import com.mpcs51205.userservice.service.BlockedUserService
import com.mpcs51205.userservice.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user/admin")
class AdminController(val userService: UserService, val blockedUserService: BlockedUserService) {

    @PutMapping("/{userId}/suspend")
    fun suspendUser(@PathVariable userId: UUID) = userService.updateUserStatus(userId, false)

    @PutMapping("/{userId}/unsuspend")
    fun unsuspendUser(@PathVariable userId: UUID) = userService.updateUserStatus(userId, true)

    @PostMapping("/{userId}/block")
    fun blockUser(@PathVariable userId: UUID) = userService.blockUser(userId)

    @DeleteMapping("/{email}/block")
    fun unblockUser(@PathVariable email: String) = userService.unblockUser(email)

}