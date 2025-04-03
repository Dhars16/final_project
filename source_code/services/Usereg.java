package com.spring.graph.api.services;


import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.graph.api.entity.User;
import com.spring.graph.api.repository.Userrepo;


@Service
public class Usereg {

	
	@Autowired
	private Userrepo userrepo;
	
	public User saveUsers(User us) {
		// TODO Auto-generated method stub
		return userrepo.save(us);
	}
	

public User updateduserstatus(User ok) {
		
		return userrepo.save(ok);
	}

		public User getuserid(Long id) {
			
			  Optional<User> seller = userrepo.findById(id);
		        return seller.orElse(null);

		
		}
		
	
	public User getUserByEmailAndPassword(String email, String password) {
		
		
		 System.out.println("qqqqqqqq");
		Optional<User> userOptional = userrepo.findByEmailAndPassword(email, password);
		 if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            

	            if (user.getStatus().equalsIgnoreCase("Approved")) {
	              
	            	
	            	  System.out.println("Seller status is  approved for email: " + email);
	            	
	            	return user;
	            } else {
	                // If status is not "Approved", print a message and return null
	                System.out.println("Seller status is not approved for email: " + email);
	                return null;
	            }
	        } else {
	            System.out.println("User not found for email: " + email);
	            return null;
	        }
	

		
	
	
	}


	
	
}
