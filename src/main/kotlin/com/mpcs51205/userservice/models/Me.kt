package com.mpcs51205.userservice.models

import java.util.*


class Me (user:User) {
    var id: UUID = user.id!!
    var name = user.name
    var email = user.email
    var paymentMethod = user.paymentMethod
    var homeAddress = user.homeAddress
    var admin= user.admin
    var roles = user.getRoles()
    var active = user.active
}