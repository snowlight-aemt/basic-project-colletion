package me.snowlight.stomp.handle

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.annotation.Order
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.stereotype.Component
import java.lang.reflect.Type

@Component
@Order(value = 1)
class ClientWebSocketStompSessionHandler(
    val objectMapper: ObjectMapper,
): StompSessionHandlerAdapter() {
    companion object {
        private const val SUBSCRIPTION_TOPIC = "/topic/chatting"
        private const val PUBLISH_DEST = "/app/chatting-message"
    }

    @Override
    override fun handleFrame(headers: StompHeaders, payload: Any?) {
        println(">>> handleFrame, $headers")

        // 구독한 채널의 메세지 받
        val responseRawMessage = String(payload as ByteArray)
        val responseMessage = objectMapper.readValue(responseRawMessage, ResponseMessage::class.java)

        println("content = ${responseMessage.content}")
    }

    @Override
    override fun getPayloadType(headers: StompHeaders): Type {
        return Any::class.java
    }

    @Override
    override fun afterConnected(session: StompSession, connectedHeaders: StompHeaders) {
        println(">>> afterConnected")

        // 구독
        session.subscribe(SUBSCRIPTION_TOPIC, this)
        val params: MutableMap<String, Any> = HashMap()
        params["message"] = "Web Socket Client 1"

        session.send(PUBLISH_DEST, params)
        println("params = $params")
    }

    @Override
    override fun handleException(
        session: StompSession,
        command: StompCommand?,
        headers: StompHeaders,
        payload: ByteArray,
        exception: Throwable
    ) {
        println(">>> handleException")
        println("exception : $exception")
    }

    @Override
    override fun handleTransportError(session: StompSession, exception: Throwable) {
        println(">>> handleTransportError, ${exception.message}")
    }
}