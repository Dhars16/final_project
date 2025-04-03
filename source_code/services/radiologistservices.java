package com.spring.graph.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.graph.api.entity.radiologistentity;

import com.spring.graph.api.repository.radiologistrepo;

@Service
public class radiologistservices {
	
	@Autowired
	private radiologistrepo radiologistrepo;

	
	 public boolean checkEmail(String email) {
			// TODO Auto-generated method stub
			return radiologistrepo.existsByEmail(email);
		}
	 
	 

public radiologistentity updatedradiologiststatus(radiologistentity seller) {
		
		return radiologistrepo.save(seller);
	}

		public radiologistentity getradiologyid(Long id) {
			
			  Optional<radiologistentity> seller = radiologistrepo.findById(id);
		        return seller.orElse(null);

		
		}
	 
	 
	 
	 

		public radiologistentity getdocByEmailAndPassword(String sellemail, String sellpassword) {
			// TODO Auto-generated method stub
			
			

			 System.out.println("qqqqqqqq");
				Optional<radiologistentity> userOptional = radiologistrepo.findByEmailAndPassword(sellemail, sellpassword);
				 if (userOptional.isPresent()) {
					 radiologistentity user = userOptional.get();
			         
			            if (user.getStatus().equalsIgnoreCase("Approved")) {
			              
			            	
			            	  System.out.println("Radiologist status is  approved for email: " + sellemail);
			            	
			            	return user;
			            } else {
			                // If status is not "Approved", print a message and return null
			                System.out.println("Radiologist status is not approved for email: " + sellemail);
			                return null;
			            }
			        } else {
			            System.out.println("User not found for email: " + sellemail);
			            return null;
			        }
			    }
	 
	 
	 
	
}
