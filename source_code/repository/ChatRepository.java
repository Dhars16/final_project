package com.spring.graph.api.repository;

import com.spring.graph.api.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByDoctorIdAndUserIdOrderByTimestamp(Long doctorId, Long userId);
    
    @Query("SELECT DISTINCT c.userId FROM ChatMessage c WHERE c.doctorId = :doctorId")
    List<Long> findDistinctUsersByDoctorId(@Param("doctorId") Long doctorId);
    

}
