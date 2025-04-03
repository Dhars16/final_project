package com.spring.graph.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.graph.api.entity.sdocrequet;
import com.spring.graph.api.entity.surgeonentity;
import com.spring.graph.api.repository.surgeonrepo;

@Service
public class surgeonservices {

	@Autowired
	private surgeonrepo surgeonrepo;
		

	 public boolean checkEmail(String email) {
			// TODO Auto-generated method stub
			return surgeonrepo.existsByEmail(email);
		}

	 


public surgeonentity updatedsurgeonstatus(surgeonentity seller) {
		
		return surgeonrepo.save(seller);
	}

		public surgeonentity getsurgeonid(Long id) {
			
			  Optional<surgeonentity> seller = surgeonrepo.findById(id);
		        return seller.orElse(null);

		
		}
		
		
		

		public surgeonentity getdocByEmailAndPassword(String sellemail, String sellpassword) {
			// TODO Auto-generated method stub
			
			

			 System.out.println("qqqqqqqq");
				Optional<surgeonentity> userOptional = surgeonrepo.findByEmailAndPassword(sellemail, sellpassword);
				 if (userOptional.isPresent()) {
					 surgeonentity user = userOptional.get();
			         
			            if (user.getStatus().equalsIgnoreCase("Approved")) {
			              
			            	
			            	  System.out.println("Surgeon status is  approved for email: " + sellemail);
			            	
			            	return user;
			            } else {
			                // If status is not "Approved", print a message and return null
			                System.out.println("Surgeon status is not approved for email: " + sellemail);
			                return null;
			            }
			        } else {
			            System.out.println("Surgeon not found for email: " + sellemail);
			            return null;
			        }
			    }
	 
	 
	 
	 
	 
	 
	
}

