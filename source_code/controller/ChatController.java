package com.spring.graph.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.spring.graph.api.entity.ChatMessage;
import com.spring.graph.api.repository.ChatRepository;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @GetMapping("/chat/{doctorId}")
    public String chatWithDoctor(@PathVariable Long doctorId, HttpSession session, Model model) {
        Object userIdObj = session.getAttribute("uid");
        if (userIdObj == null) {
            return "redirect:/login"; // Redirect if user is not logged in
        }
        Long userId = Long.parseLong(userIdObj.toString()); // Ensure consistent type
        
        System.out.println("User ID: " + userId);

        List<ChatMessage> messages = chatRepository.findByDoctorIdAndUserIdOrderByTimestamp(doctorId, userId);

        model.addAttribute("doctorId", doctorId);
        model.addAttribute("userId", userId);
        model.addAttribute("messages", messages);

        return "chat"; // This maps to chat.html
    }

    @PostMapping("/chat/send")
    public String sendMessage(@RequestParam Long doctorId, @RequestParam String message, HttpSession session) {
        Object userIdObj = session.getAttribute("uid");
        if (userIdObj == null) {
            return "redirect:/ulogin"; // Redirect if not logged in
        }
        Long userId = Long.parseLong(userIdObj.toString());

        ChatMessage chatMessage = new ChatMessage(doctorId, userId, "user", message);
        chatRepository.save(chatMessage);

        return "redirect:/chat/" + doctorId;
    }
    
    
    
    @GetMapping("/doctor/chat")
    public String viewDoctorChats(HttpSession session, Model model) {
        Object doctorIdObj = session.getAttribute("did");  // Retrieve logged-in doctor's ID
        if (doctorIdObj == null) {
            return "redirect:/dlogin"; // Redirect if doctor is not logged in
        }

        Long doctorId = Long.parseLong(doctorIdObj.toString());
        
        // Get distinct user IDs who have chatted with this doctor
        List<Long> userIds = chatRepository.findDistinctUsersByDoctorId(doctorId);
        
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("userIds", userIds);

        return "doctor_chat_list"; // Create doctor_chat_list.html for viewing chat users
    }
    
    
    
    
  
    @GetMapping("/doctor/chat/{userId}")
    public String viewChatWithUser(@PathVariable Long userId, HttpSession session, Model model) {
        Object doctorIdObj = session.getAttribute("did"); // Use "did" for consistency
        if (doctorIdObj == null) {
            return "redirect:/dlogin"; 
        }

        Long doctorId = Long.parseLong(doctorIdObj.toString());

        // Fetch messages where doctorId and userId match
        List<ChatMessage> messages = chatRepository.findByDoctorIdAndUserIdOrderByTimestamp(doctorId, userId);

        model.addAttribute("doctorId", doctorId);
        model.addAttribute("userId", userId);
        model.addAttribute("messages", messages);

        return "chats"; // chats.html will display messages
    }

    @PostMapping("/doctor/chat/{userId}/send")
    public String sendMessage1(@PathVariable Long userId, @RequestParam String message, HttpSession session) {
        Object doctorIdObj = session.getAttribute("did"); // Use "did" for consistency
        if (doctorIdObj == null) {
            return "redirect:/dlogin";
        }

        Long doctorId = Long.parseLong(doctorIdObj.toString());

        ChatMessage newMessage = new ChatMessage();
        newMessage.setDoctorId(doctorId);
        newMessage.setUserId(userId);
        newMessage.setSender("Doctor");  // Mark the sender
        newMessage.setMessage(message);
        newMessage.setTimestamp(LocalDateTime.now());

        chatRepository.save(newMessage); 

        return "redirect:/doctor/chat/" + userId; // Refresh the chat
    }

}


