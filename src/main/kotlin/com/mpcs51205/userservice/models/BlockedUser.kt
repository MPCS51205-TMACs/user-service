package com.mpcs51205.userservice.models

import java.util.*
import javax.persistence.*

@Entity
class BlockedUser {
    @Id
    @Column(nullable = false)
    var email: String? = null

}