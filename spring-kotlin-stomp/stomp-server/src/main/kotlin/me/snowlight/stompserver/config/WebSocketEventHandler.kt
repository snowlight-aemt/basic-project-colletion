package me.snowlight.stompserver.config

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import org.springframework.web.socket.messaging.SessionSubscribeEvent
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent

@Component
class WebSocketEventHandler {
    @EventListener
    fun handleWebSocketSessionConnectEventListener(event: SessionConnectEvent) {
        println(">>> Received a SessionConnectEvent")
    }

    @EventListener
    fun handleWebSocketSessionSubscribeEventListener(event: SessionSubscribeEvent) {
        println(">>> Received a SessionSubscribeEvent")
    }

    @EventListener
    fun handleWebSocketSessionUnsubscribeEventListener(event: SessionUnsubscribeEvent) {
        println(">>> Received a SessionUnsubscribeEvent")
    }

    @EventListener
    fun handleWebSocketSessionDisconnectEvent(event: SessionDisconnectEvent) {
        println(">>> Received a SessionDisconnectEvent")
    }
}