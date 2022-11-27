package com.mpcs51205.userservice.event

import com.mpcs51205.userservice.models.User
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*

@Component
class RabbitPublisher {
    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @Autowired
    lateinit var rabbitConfig: RabbitConfig

    fun sendCreateEvent(user: User) = send(exchange = rabbitConfig.createExchange, payload = user)
    fun sendUpdateEvent(userUpdateEvent: UserUpdateEvent) = send(exchange = rabbitConfig.updateExchange, payload = userUpdateEvent)
    fun sendDeleteEvent(userId: UUID) = send(exchange = rabbitConfig.deleteExchange, payload = UserDeleteEvent(userId))
    fun sendActivationEvent(userId: UUID, isActive:Boolean) = send(exchange = rabbitConfig.activationExchange, payload = UserActivationEvent(userId, isActive))

    private fun send(exchange: String, payload: Serializable) {
        rabbitTemplate.convertAndSend(exchange, "", payload)
    }


}