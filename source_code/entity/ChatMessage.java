package com.spring.graph.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private Long doctorId;
    private String sender;
    private String message;
    private LocalDateTime timestamp;

    public ChatMessage() {}

    public ChatMessage(Long doctorId, Long userId, String sender, String message) {
        this.doctorId = doctorId;
        this.userId = userId;
        this.sender = sender;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public Long getDoctorId() { return doctorId; }
    public String getSender() { return sender; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public void setSender(String sender) { this.sender = sender; }
    public void setMessage(String message) { this.message = message; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
