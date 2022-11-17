package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.AuthRequest
import com.mpcs51205.userservice.models.User
import com.mpcs51205.userservice.models.UserUpdate
import com.mpcs51205.userservice.service.BlockedUserService
import com.mpcs51205.userservice.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService, val blockedUserService: BlockedUserService) {
    // TODO: endpoint permissions

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId:UUID): User = userService.getUserById(userId)

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = userService.deleteUser(userId)

    @PutMapping("/{userId}")
    fun updateUser(@RequestBody userUpdate: UserUpdate, @PathVariable userId: UUID) = userService.updateUser(userUpdate, userId)

}