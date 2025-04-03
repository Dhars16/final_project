package com.spring.graph.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.graph.api.entity.Docterreg;
import com.spring.graph.api.entity.sdocrequet;
import com.spring.graph.api.repository.patientdocrepo;

@Service
public class patientdocservice {
	 @Autowired
	    private patientdocrepo prepo;
	    
	    
	

	public sdocrequet getsid(Long id) {
		
		  Optional<sdocrequet> seller = prepo.findById(id);
	        return seller.orElse(null);

	
	}


	
public sdocrequet updatesid(sdocrequet seller) {
		
		return prepo.save(seller);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
