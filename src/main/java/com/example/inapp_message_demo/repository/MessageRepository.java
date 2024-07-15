package com.example.inapp_message_demo.repository;

import com.example.inapp_message_demo.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiverIdAndStatus(String receiverId, String status);
}
