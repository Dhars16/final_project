package com.spring.graph.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.graph.api.entity.radiologistentity;



@Repository
public interface radiologistrepo  extends JpaRepository<radiologistentity, Long> {
	

	public boolean existsByEmail(String email);

	
	 public String save(radiologistrepo radiologistrepo);

	
	  List<radiologistentity> findByStatus(String status);
		
	
	  Optional<radiologistentity> findByEmailAndPassword(String email, String password);
	
	
	 

}
