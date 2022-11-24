package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.AuthRequest
import com.mpcs51205.userservice.models.User
import com.mpcs51205.userservice.models.UserUpdate
import com.mpcs51205.userservice.service.BlockedUserService
import com.mpcs51205.userservice.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID): User = userService.getUserById(userId)

    @DeleteMapping
    fun deleteUser(authentication: Authentication) {
        val userId = UUID.fromString(authentication.name)
        userService.deleteUser(userId)
    }

    @PutMapping
    fun updateUser(@RequestBody userUpdate: UserUpdate, authentication: Authentication) {
        val userId = UUID.fromString(authentication.name)
        userService.updateUser(userUpdate, userId)
    }

}