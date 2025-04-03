package com.spring.graph.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.graph.api.entity.Patiententity;
import com.spring.graph.api.entity.sdocrequet;


@Repository
public interface Surgeon2 extends JpaRepository<Patiententity, Long> {

	// If using JPA Repository
	List<Patiententity> findAllByStatus(String status);


	List <Patiententity> findByStatusAndPatientid(String email,String patientid);

	

}


