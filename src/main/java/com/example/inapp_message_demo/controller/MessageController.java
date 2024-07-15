package com.example.inapp_message_demo.controller;


import com.example.inapp_message_demo.model.Message;
import com.example.inapp_message_demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(@RequestParam String senderId, @RequestParam String receiverId, @RequestParam String content) throws Exception {
        messageService.sendMessage(senderId, receiverId, content);
    }

    @GetMapping("/unread")
    public List<Message> getUnreadMessages(@RequestParam String receiverId) {
        return messageService.getUnreadMessages(receiverId);
    }
}
