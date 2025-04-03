package com.spring.graph.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.graph.api.entity.sdocrequet;
@Repository
public interface udownload extends JpaRepository <sdocrequet, Long> {

	

	    List<sdocrequet> findByPidAndStatus1AndDepartmentIn(String pid, String status1, List<String> departments);
	
	
	
}
