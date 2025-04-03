package com.spring.graph.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.graph.api.entity.pharmacyentity;



public interface pharmacyrepo  extends JpaRepository<pharmacyentity, Long>  {

	public boolean existsByEmail(String email);


	
	
		List<pharmacyentity> findByStatus(String status);

	 
	  Optional<pharmacyentity> findByEmailAndPassword(String email, String password);
	
	
	  public String save(pharmacyrepo pharmacyrepo);
	
	
	
	
	
}
