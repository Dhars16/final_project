package com.spring.graph.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.graph.api.entity.Docterreg;
import com.spring.graph.api.repository.Docregrepository;

@Service
public class Docservice {

	@Autowired
	private Docregrepository  docrepo;
	
	
	 public boolean checkEmail(String email) {
			// TODO Auto-generated method stub
			return docrepo.existsByEmail(email);
		}
	 

public Docterreg updatedoctorstatus(Docterreg seller) {
		
		return docrepo.save(seller);
	}

		public Docterreg getdoctorid(Long id) {
			
			  Optional<Docterreg> seller = docrepo.findById(id);
		        return seller.orElse(null);

		
		}
		
		
		

		public Docterreg getdocByEmailAndPassword(String sellemail, String sellpassword) {
			// TODO Auto-generated method stub
			
			

			 System.out.println("qqqqqqqq");
				Optional<Docterreg> userOptional = docrepo.findByEmailAndPassword(sellemail, sellpassword);
				 if (userOptional.isPresent()) {
					 Docterreg user = userOptional.get();
			         
			            if (user.getStatus().equalsIgnoreCase("Approved")) {
			              
			            	
			            	  System.out.println("Seller status is  approved for email: " + sellemail);
			            	
			            	return user;
			            } else {
			                // If status is not "Approved", print a message and return null
			                System.out.println("Seller status is not approved for email: " + sellemail);
			                return null;
			            }
			        } else {
			            System.out.println("User not found for email: " + sellemail);
			            return null;
			        }
			    }
			
		  public List<Docterreg> getAllDoctors() {
		        return docrepo.findAll(); // This should return a list of doctors
		    }
		
		}


