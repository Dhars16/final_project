package com.spring.graph.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.graph.api.entity.Patiententity;
import com.spring.graph.api.entity.sdocrequet;

@Repository
public interface patientdocrepo  extends JpaRepository <sdocrequet, Long> {

	
	public String save(patientdocrepo docrepo);

	public List<sdocrequet> findByStatus1AndDepartment(String string, String string2);

	List<sdocrequet> findBySidAndStatus1AndDepartmentIn(String sid, String status1, List<String> departments);

	
	
}
