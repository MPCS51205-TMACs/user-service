package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.*
import com.mpcs51205.userservice.service.BlockedUserService
import com.mpcs51205.userservice.service.UserService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @GetMapping("/all")
    fun getUsers(@RequestParam mini: Boolean = false): List<NameAndId> = userService.getUsers().map { if (mini) it.miniaturize() else it }

    // returns sensitive information to just "me" (not other users)
    @GetMapping
    fun getMe(authentication: Authentication): Me = Me(userService.getUserById(UUID.fromString(authentication.name)))

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