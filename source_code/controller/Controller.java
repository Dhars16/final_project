package com.spring.graph.api.controller;


import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

import java.util.List;

import java.io.File;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.multipart.MultipartFile;


import com.spring.graph.api.entity.Adminentity;
import com.spring.graph.api.entity.Docterreg;
import com.spring.graph.api.entity.User;
import com.spring.graph.api.entity.sdocrequet;
import com.spring.graph.api.repository.Docregrepository;
import com.spring.graph.api.repository.patientdocrepo;
import com.spring.graph.api.services.Docservice;
import com.spring.graph.api.services.patientdocservice;


import jakarta.servlet.http.HttpSession;



@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	Docservice docservice;
	@Autowired
	private Docregrepository docrepo;

	 @Autowired
	    private patientdocrepo prepo;
	    
	 @Autowired
	    private patientdocservice pservice;
	    
	 private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    private static final int KEY_LENGTH = 5;

	    
	@GetMapping("/")
	public String index() {

		return "index";
	}

	@GetMapping("/Docloginn")
	public String home() {

		return "Doctors";
	}
	
	
	
	
	
	@GetMapping("/drequest")
	public String docreq() {

		return "drequest";
	}
	

	@GetMapping("/logout")
	public String logout() {

		return "Doctors";
	}

	@GetMapping("/adminlogin")
	public String adminlogin() {

		return "Adminlogin";
	}

	@GetMapping("/docreg")
	public String docreg() {

		return "docterreg";
	}
	
	
	
	//

	@PostMapping("/docterreg")
	public String docterreg(@RequestParam("name") String name, @RequestParam("image") MultipartFile Image,
			@RequestParam("name") String Lname, @RequestParam("Email") String Email,
			@RequestParam("password") String password, 
			@RequestParam("doctors") String doctors,
			@RequestParam("contact") String contact, 
		 Model model) {
		// Create a new Dress object

		Docterreg ob = new Docterreg();

		ob.setName(name);
		ob.setStatus("request");
		ob.setEmail(Email);
		ob.setDoctors(doctors);
		
		ob.setPassword(password);
		ob.setContact(contact);
		
		
		
		
		
		
		
		
		
		boolean a = docservice.checkEmail(Email);

		String message = (a == true) ? "This Mail was Already Exist.." : "Register successfully..";
		model.addAttribute("msg", message);

		if (a == false) {

			try {
				// Save the dress image
				String fileName = Image.getOriginalFilename();
				byte[] imageBytes = Image.getBytes();

				// Specify the directory where the image will be saved
				String directory = "src/main/resources/static/images/";

				// Create the directory if it doesn't exist
				Files.createDirectories(Paths.get(directory));

				// Specify the image file path
				Path imagePath = Paths.get(directory + fileName);

				// Write the image bytes to the specified path
				Files.write(imagePath, imageBytes);

				ob.setImage(fileName);
			} catch (IOException e) {
				// Handle the exception (e.g., log the error, show an error message)
				e.printStackTrace();
			}

			docrepo.save(ob);

			return "Doctors";

		} else {

			return "error";

		}


	}
	
	
	//Doctor login logics
	 @PostMapping("/dlog")
	   public String loginus(Docterreg sell, Model model,HttpSession session) {
	   //UserDtls user = new UserDtls(); 
	  
		   System.out.println(sell.getEmail());
		   System.out.println(sell.getPassword());
		   
		   Docterreg st=docservice.getdocByEmailAndPassword(sell.getEmail(), sell.getPassword());
	   if (st != null) {
	       // Session attributes are already set in UserService
		  
		   session.setAttribute("did", st.getId());  // doctor id 

		   System.out.println(sell.getEmail());
		
	       // Add email attribute to the model
			
		   return "docinterface";

	   } else {
		   
		   
		   System.out.println(st);
		   
		   return "Doctors";

	 }
	   
	   }
	   
	
	
		
	//admin
	
		@PostMapping("/adminauthentication")
		public String loginUser(@ModelAttribute Adminentity hos, Model m) {

			
			
			
			
			if (hos.getEmail().equals("admin@gmail.com") && hos.getPassword().equals("admin")) {

				System.out.println("success");

				return "adminhome";
			} else {

				System.out.println("failure");

				m.addAttribute("msg", "Invalid username or password");

				return "error";
			}

		}
	
		@GetMapping("/viewrequest")
		/* public String viewpatient(HttpSession session, Model model) { */

			

		/*
		 * Docterreg existingUser = docrepo.findByStatus("request");
		 * 
		 * if (existingUser != null) {
		 * 
		 * model.addAttribute("doctorRequests", existingUser);
		 * System.out.println(" userappointment  " + existingUser.toString());
		 * 
		 * return "drequest";
		 * 
		 * 
		 * 
		 * } else { System.out.println("No user found with status 'request'"); return
		 * "nodata"; // Return a view indicating no data found }
		 * 
		 * }
		 */
		 public String viewusers(HttpSession session, Model model) {
	    	 List<Docterreg> existingUser = docrepo.findByStatus("request");

	        if (existingUser != null) {
	            model.addAttribute("doctorRequests", existingUser);
	            System.out.println("User appointment: " + existingUser.toString());
	            
	            return "drequest";
	        } 
	        else {
	        	// Handle case where no data is found
	            System.out.println("No user found with status 'request'");
	            return "nodata";
	        }
	    }

	
	
		 @GetMapping("/viewPDF/{filename}")
		    public ResponseEntity<FileSystemResource> viewPDF(@PathVariable("filename") String filename) {
		        // Directory where PDF files are stored
		        String directory = "src/main/resources/static/images/";

		        // File path based on filename
		        String filePath = directory + filename;

		        // Create a file object
		        File file = new File(filePath);

		        // Check if the file exists
		        if (file.exists()) {
		            // Set content type as application/pdf
		            HttpHeaders headers = new HttpHeaders();
		            headers.setContentType(MediaType.APPLICATION_PDF);

		            // Return ResponseEntity with the file
		            return ResponseEntity.ok()
		                    .headers(headers)
		                    .body(new FileSystemResource(file));
		        } else {
		            // Return ResponseEntity with 404 Not Found status
		            return ResponseEntity.notFound().build();
		        }
		    }
		
		 //doc request for approve and reject status
		 @GetMapping("/drequest/{id}")
			public String updateStatus(@PathVariable Long id, Model model) {

				Docterreg sel = docservice.getdoctorid(id);
				  if (sel != null) {
			            sel.setStatus("Approved");
			            docservice.updatedoctorstatus(sel);
			        }
			        return "drequest";
			    }

		 @GetMapping("/dqrequest/{id}")
			public String updatedStatus(@PathVariable Long id, Model model) {

				Docterreg sel = docservice.getdoctorid(id);
				  if (sel != null) {
			            sel.setStatus("rejected");
			            docservice.updatedoctorstatus(sel);
			        }
			        return "drequest";
			    }

		 
		 
		 
		 @GetMapping("/patientreportsrequstt")
			public String viewpatientdetails(HttpSession session, Model model) {

				

			 List<sdocrequet> existingUsers = prepo.findByStatus1AndDepartment("request", "surgeon");

		        if (!existingUsers.isEmpty()) {
		            model.addAttribute("filter", existingUsers);
		            System.out.println("User appointments: " + existingUsers.toString());
		            return "surgeondocrequest";
		        } else {
		            System.out.println("No user found with status 'update' and department 'departmentName'");
		            return "surgeondocrequest";// Return a view indicating no data found
		        }
		    }
			
		
		 
		 
		 
		 @GetMapping("/radiopatientreportsrequst")
			public String viewpatientradiodetails(HttpSession session, Model model) {

				

			 List<sdocrequet> existingUsers = prepo.findByStatus1AndDepartment("request", "radiology");

		        if (!existingUsers.isEmpty()) {
		            model.addAttribute("filter", existingUsers);
		            System.out.println("User appointments: " + existingUsers.toString());
		            return "radiodocrequest";
		        } else {
		            System.out.println("No user found with status 'update' and department 'departmentName'");
		            return "nodata"; // Return a view indicating no data found
		        }
		    }
			
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		
		 
		 @GetMapping("/pharmpatientreportsrequst")
			public String pharm(HttpSession session, Model model) {

				

			 List<sdocrequet> existingUsers = prepo.findByStatus1AndDepartment("request", "pharmacy");

		        if (!existingUsers.isEmpty()) {
		            model.addAttribute("filter", existingUsers);
		            System.out.println("User appointments: " + existingUsers.toString());
		            return "pharmdocrequest";
		        } else {
		            System.out.println("No user found with status 'update' and department 'departmentName'");
		            return "nodata"; // Return a view indicating no data found
		        }
		    }
			
		 @GetMapping("/userpatientreportsrequst")
			public String userpatientreportsrequst(HttpSession session, Model model) {

				

			 List<sdocrequet> existingUsers = prepo.findByStatus1AndDepartment("request", "user");

		        if (!existingUsers.isEmpty()) {
		            model.addAttribute("filter", existingUsers);
		            System.out.println("User appointments: " + existingUsers.toString());
		            return "userdocrequest";
		        } else {
		            System.out.println("No user found with status 'update' and department 'departmentName'");
		            return "nodata"; // Return a view indicating no data found
		        }
		    }
			
		 
		 
		 
		 
		 
		 
		 @GetMapping("/saccept/{id}")
			public String update(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Approved");
			           
			            SecureRandom random = new SecureRandom();
			            StringBuilder key = new StringBuilder(KEY_LENGTH);

			            for (int i = 0; i < KEY_LENGTH; i++) {
			                int index = random.nextInt(CHARACTERS.length());
			                key.append(CHARACTERS.charAt(index));
			            }

			            System.out.println("Generated 5-character random key: " + key.toString());
			            
			            sel.setStatus2( key.toString());
			            pservice.updatesid(sel);
			        }
				  return "docinterface";
			    }

		 
		 
		 @GetMapping("/sreject/{id}")
			public String reject(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Rejected");
			           
			           
			            sel.setStatus2("reject");
			            pservice.updatesid(sel);
			        }
				  return "docinterface";
			    }

		 @GetMapping("/raccept/{id}")
			public String raccept(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Approved");
			           
			            SecureRandom random = new SecureRandom();
			            StringBuilder key = new StringBuilder(KEY_LENGTH);

			            for (int i = 0; i < KEY_LENGTH; i++) {
			                int index = random.nextInt(CHARACTERS.length());
			                key.append(CHARACTERS.charAt(index));
			            }

			            System.out.println("Generated 5-character random key: " + key.toString());
			            
			            sel.setStatus2( key.toString());
			            pservice.updatesid(sel);
			        }
				  return "docinterface";
			    }

		 

		 
		 
		 
		 @GetMapping("/rreject/{id}")
			public String rreject(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Rejected");
			           
			           
			            sel.setStatus2("reject");
			            pservice.updatesid(sel);
			        }
				  return "docinterface";
			    }

		 
		 
		 
		 @GetMapping("/paccept/{id}")
			public String paccept(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Approved");
			           
			            SecureRandom random = new SecureRandom();
			            StringBuilder key = new StringBuilder(KEY_LENGTH);

			            for (int i = 0; i < KEY_LENGTH; i++) {
			                int index = random.nextInt(CHARACTERS.length());
			                key.append(CHARACTERS.charAt(index));
			            }

			            System.out.println("Generated 5-character random key: " + key.toString());
			            
			            sel.setStatus2( key.toString());
			            pservice.updatesid(sel);
			        }
				  return "docinterface";
			    }

		 

		 
		 
		 
		 @GetMapping("/preject/{id}")
			public String preject(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Rejected");
			           
			           
			            sel.setStatus2("reject");
			            pservice.updatesid(sel);
			      
				  }
				  return "docinterface";
			    }

		 
		 
		 
		 
		 
		 
		 
		 @GetMapping("/uaccept/{id}")
			public String uaccept(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Approved");
			           
			            SecureRandom random = new SecureRandom();
			            StringBuilder key = new StringBuilder(KEY_LENGTH);

			            for (int i = 0; i < KEY_LENGTH; i++) {
			                int index = random.nextInt(CHARACTERS.length());
			                key.append(CHARACTERS.charAt(index));
			            }

			            System.out.println("Generated 5-character random key: " + key.toString());
			            
			            sel.setStatus2( key.toString());
			            pservice.updatesid(sel);
			        }
				  return "docinterface";
			    }

		 

		 
		 
		 
		 @GetMapping("/ureject/{id}")
			public String ureject(@PathVariable Long id, Model model) {

				sdocrequet sel = pservice.getsid(id);
				  if (sel != null) {
			            sel.setStatus1("Rejected");
			           
			           
			            sel.setStatus2("reject");
			            pservice.updatesid(sel);
			      
				  }
				  return "docinterface";
			    }

		 
		 
		 
		 @GetMapping("/doctors")
		    public String listDoctors(Model model) {
		        List<Docterreg> doctors = docservice.getAllDoctors();
		        model.addAttribute("doctors", doctors); // Pass the list to Thymeleaf
		        return "doctorslist"; // Ensure this matches your HTML filename
		    }
		 
		
		 
		 
		 
		 
}
