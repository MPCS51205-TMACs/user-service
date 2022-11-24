package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.User
import com.mpcs51205.userservice.service.UserService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/register")
class RegistrationController(val userService: UserService) {
    @PostMapping
    fun createUser(@RequestBody user: User): User = userService.createUser(user)

}