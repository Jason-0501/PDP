package com.example.policy.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class AuthorizationController {

    // 接收來自 PEP 的 POST 請求
    @PostMapping(consumes = "text/plain")
    public ResponseEntity<String> receiveMessage(@RequestBody String message) {
        // 打印接收到的消息到控制台
        System.out.println("Received message from PEP: " + message);
        
        // 返回一個簡單的確認回應
        return ResponseEntity.ok("Message received");
    }
}