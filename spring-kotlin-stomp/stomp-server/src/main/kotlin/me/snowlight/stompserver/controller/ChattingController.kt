package me.snowlight.stompserver.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.util.HtmlUtils

@Controller
class ChattingController {
    @MessageMapping("/chatting-message")
    @SendTo("/topic/chatting")
    fun message(chattingMessage: ChattingMessage) : ChattingResponse {
        return ChattingResponse(HtmlUtils.htmlEscape(chattingMessage.message))
    }
}