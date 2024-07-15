package com.example.inapp_message_demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class InAppMessageDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    private static final String BASE_URL = "http://localhost:8080/messages";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        // 发送消息
        String sendMessageUrl = BASE_URL + "/send?senderId=user1&receiverId=user2&content=Hello from user1 to user2";
        restTemplate.postForEntity(sendMessageUrl, null, String.class);

        // 获取未读消息
        String getUnreadMessagesUrl = BASE_URL + "/unread?receiverId=user2";
        ResponseEntity<String> response = restTemplate.getForEntity(getUnreadMessagesUrl, String.class);
        System.out.println("Unread messages: " + response.getBody());
    }

}
