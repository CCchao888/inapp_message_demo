package com.example.inapp_message_demo.service;


import com.example.inapp_message_demo.model.Message;
import com.example.inapp_message_demo.repository.MessageRepository;
import com.example.inapp_message_demo.websocket.WebSocketServer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    WebSocketServer webSocketServer;

    public void sendMessage(String senderId, String receiverId, String content) throws Exception {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setStatus("UNREAD");

        messageRepository.save(message);

        cacheUnreadMessage(receiverId, message);
        redisTemplate.convertAndSend("message-notify", receiverId);
        webSocketServer.sendMessage(receiverId, content);

    }

    public List<Message> getUnreadMessages(String receiverId) {
        return messageRepository.findByReceiverIdAndStatus(receiverId, "UNREAD");
    }

    private void cacheUnreadMessage(String receiverId, Message message) {
        String messageKey = "MESSAGE:" + message.getId();
        redisTemplate.opsForHash().putAll(messageKey, Map.of(
                "sender_id", message.getSenderId(),
                "content", message.getContent(),
                "timestamp", message.getTimestamp().toString()
        ));
        redisTemplate.opsForList().leftPush("UNREAD_MESSAGES:" + receiverId, message.getId());
    }
}
