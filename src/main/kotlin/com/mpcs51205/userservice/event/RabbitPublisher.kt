package com.mpcs51205.userservice.event

import com.mpcs51205.userservice.models.User
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*

@Component
class RabbitPublisher {
    @Autowired
    lateinit var template: RabbitTemplate

    @Value("\${spring.rabbitmq.template.exchange-create}")
    lateinit var createExchange: String
    @Value("\${spring.rabbitmq.template.exchange-update}")
    lateinit var updateExchange: String
    @Value("\${spring.rabbitmq.template.exchange-delete}")
    lateinit var deleteExchange: String
    @Value("\${spring.rabbitmq.template.exchange-activation}")
    lateinit var activationExchange: String

    @Bean
    fun userCreateExchange() = FanoutExchange(createExchange, true, false)

    @Bean
    fun userUpdateExchange() = FanoutExchange(updateExchange, true, false)

    @Bean
    fun userDeleteExchange() = FanoutExchange(deleteExchange, true, false)

    @Bean
    fun userActivateExchange() = FanoutExchange(activationExchange, true, false)

    fun sendCreateEvent(user: User) = send(exchange = createExchange, payload = user)
    fun sendUpdateEvent(userUpdateEvent: UserUpdateEvent) = send(exchange = updateExchange, payload = userUpdateEvent)
    fun sendDeleteEvent(userId: UUID) = send(exchange = deleteExchange, payload = UserDeleteEvent(userId))
    fun sendActivationEvent(userId: UUID, isActive:Boolean) = send(exchange = activationExchange, payload = UserActivationEvent(userId, isActive))

    private fun send(exchange: String, payload: Serializable) {
        template.convertAndSend(exchange, "", payload)
    }


}