package com.example.onlineauction.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class WsController {

    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        String name = "MAX"; // new Gson().fromJson(message, Map.class).get("name").toString();
        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
        return "MAX";
    }

    @MessageMapping("/bid")
    @SendTo("/topic/bids")
    public String processMessage(@Payload String message) throws Exception {
//        String name = "MAX"; // new Gson().fromJson(message, Map.class).get("name").toString();
//        messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
        return "MAX";
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
