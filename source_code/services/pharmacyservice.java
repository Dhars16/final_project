package com.spring.graph.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.graph.api.entity.pharmacyentity;

import com.spring.graph.api.repository.pharmacyrepo;

@Service
public class pharmacyservice {
	
	@Autowired
	private pharmacyrepo pharmacyrepo;

	
	 public boolean checkEmail(String email) {
			// TODO Auto-generated method stub
			return pharmacyrepo.existsByEmail(email);
		}
	 
	 

public pharmacyentity updatedpharmacystatus(pharmacyentity seller) {
		
		return pharmacyrepo.save(seller);
	}

		public pharmacyentity getpharmacyid(Long id) {
			
			  Optional<pharmacyentity> seller = pharmacyrepo.findById(id);
		        return seller.orElse(null);

		
		}
		
		public pharmacyentity getdocByEmailAndPassword(String sellemail, String sellpassword) {
			// TODO Auto-generated method stub
			
			

			 System.out.println("qqqqqqqq");
				Optional<pharmacyentity> userOptional = pharmacyrepo.findByEmailAndPassword(sellemail, sellpassword);
				 if (userOptional.isPresent()) {
					 pharmacyentity user = userOptional.get();
			         
			            if (user.getStatus().equalsIgnoreCase("Approved")) {
			              
			            	
			            	  System.out.println("pharmacy status is  approved for email: " + sellemail);
			            	
			            	return user;
			            } else {
			                // If status is not "Approved", print a message and return null
			                System.out.println("pharmacy status is not approved for email: " + sellemail);
			                return null;
			            }
			        } else {
			            System.out.println("pharmacy not found for email: " + sellemail);
			            return null;
			        }
			    }
	 
	  

}
