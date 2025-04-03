package com.spring.graph.api.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.graph.api.entity.surgeonentity;
@Repository
public interface surgeonrepo extends JpaRepository<surgeonentity, Long> {
	
	
	public boolean existsByEmail(String email);
	
	

	public String save(surgeonrepo surgeonrepo);
	
	
	List<surgeonentity> findByStatus(String status);

	
	 Optional<surgeonentity> findByEmailAndPassword(String sellemail, String sellpassword);
	
	
	  
}
