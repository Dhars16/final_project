package com.spring.graph.api.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.graph.api.entity.User;
import com.spring.graph.api.entity.surgeonentity;



@Repository
public interface Userrepo extends JpaRepository<User, Long> {

	
	public boolean existsByEmail(String email);

	public String save(Userrepo userrepo);
	
	/* public User findByStatus(String email); */
	  List<User> findByStatus(String status);
	 
	  Optional<User> findByEmailAndPassword(String email, String password);
	  Optional <User> findByStatusAndUserid(String status, String patientid);
	
}
