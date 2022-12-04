package com.mpcs51205.userservice.controller

import com.mpcs51205.userservice.models.Address
import com.mpcs51205.userservice.models.PaymentInfo
import com.mpcs51205.userservice.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/user/admin")
class AdminController(val userService: UserService) {

    @GetMapping("/{userId}/payment")
    fun getPaymentInfo(@PathVariable userId: UUID) = PaymentInfo(userService.getUserById(userId).paymentMethod)

    @GetMapping("/address")
    fun getAddresses(@RequestParam userIds: List<UUID>?): List<Address> = userIds?.map {userId-> Address(userService.getUserById(userId).homeAddress) }?: listOf()


    @PutMapping("/{userId}/suspend")
    fun suspendUser(@PathVariable userId: UUID) = userService.updateUserStatus(userId, false)

    @PutMapping("/{userId}/unsuspend")
    fun unsuspendUser(@PathVariable userId: UUID) = userService.updateUserStatus(userId, true)

    @PostMapping("/{userId}/block")
    fun blockUser(@PathVariable userId: UUID) = userService.blockUser(userId)

    @DeleteMapping("/{email}/block")
    fun unblockUser(@PathVariable email: String) = userService.unblockUser(email)

    @GetMapping("/suspended")
    fun getSuspendedAccounts()  = userService.getSuspendedAccounts()

}