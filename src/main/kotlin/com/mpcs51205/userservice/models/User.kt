package com.mpcs51205.userservice.models

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.mpcs51205.userservice.event.UserUpdateEvent
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "auction_user", indexes = [Index(columnList = "email")])
class User: Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(nullable = false)
    var id: UUID? = null

    @Column(nullable = false)
    lateinit var name: String

    @Column(unique=true, nullable = false)
    lateinit var email: String

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    lateinit var password: String

    @Column
    lateinit var paymentMethod: String

    @Column
    var admin: Boolean = false

    @Column
    var suspended: Boolean = false

    fun getRoles(): List<String> = if (admin) listOf("ROLE_USER","ROLE_ADMIN") else listOf("ROLE_USER")
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserUpdate: Serializable {
    var name: String? = null
    var email: String? = null
    var isAdmin: Boolean? = null
    var isSuspended: Boolean? = null

    fun update(user: User): UserUpdateEvent {
        user.name = this.name ?: user.name
        user.email = this.email ?: user.email
        user.admin = this.isAdmin ?: user.admin
        user.suspended = this.isSuspended ?: user.suspended
        return UserUpdateEvent(user.id!!, this)
    }

}

