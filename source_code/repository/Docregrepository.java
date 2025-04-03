package com.spring.graph.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.spring.graph.api.entity.Docterreg;
import com.spring.graph.api.entity.User;


@Repository
public interface Docregrepository extends JpaRepository<Docterreg, Long> {

	public boolean existsByEmail(String email);

	public String save(Docregrepository docrepo);
	
	/* public Docterreg findByStatus(String email); */

	 Optional<Docterreg> findByEmailAndPassword(String sellemail, String sellpassword);
	
	
	 List<Docterreg> findByStatus(String status);
	
	//get alll doctors
	    List<Docterreg> findAll(); // This should fetch all doctors

	
}
