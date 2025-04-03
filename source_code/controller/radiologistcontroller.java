package com.spring.graph.api.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.crypto.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.graph.api.entity.Patiententity;
import com.spring.graph.api.entity.radiologistentity;
import com.spring.graph.api.entity.sdocrequet;
import com.spring.graph.api.entity.surgeonentity;
import com.spring.graph.api.repository.Surgeon2;
import com.spring.graph.api.repository.patientdocrepo;
import com.spring.graph.api.repository.radiologistrepo;
import com.spring.graph.api.services.PatientReportService;
import com.spring.graph.api.services.radiologistservices;

import jakarta.servlet.http.HttpSession;


@org.springframework.stereotype.Controller
public class radiologistcontroller {
	  @Autowired
	    private Surgeon2 s2;

	@Autowired
	private radiologistrepo radilologistrepo;
	
	
	  @Autowired
	    private PatientReportService preportservice;
	    
	@Autowired
	private radiologistservices radiologyservices;
	  @Autowired
	    private patientdocrepo prepo;
	    
	
	@GetMapping("/Radiologistreglogin")
	public String reglogin() {

		return "radiologistreg";
	}
	

	 @PostMapping("/radilogistrlogin")
	   public String loginus(surgeonentity sell, Model model, HttpSession session) {
	   //UserDtls user = new UserDtls(); 
	  
		   System.out.println(sell.getEmail());
		   System.out.println(sell.getPassword());
		   
		   radiologistentity st=radiologyservices.getdocByEmailAndPassword(sell.getEmail(), sell.getPassword());
	   if (st != null) {
	       // Session attributes are already set in UserService
		  
		   session.setAttribute("sid", st.getRid());
		
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   
		   System.out.println(sell.getEmail());
		
	       // Add email attribute to the model
			
		   return "radiologistinterface";

	   } else {
		   
		   
		   System.out.println(st);
		   
		   return "radiologistregister";

	 }
	   
	   }
	

    @GetMapping("/rrequest")
    public String viewRadiologists(HttpSession session, Model model) {
        List <radiologistentity> existingUser = radilologistrepo.findByStatus("request");
        

        if (existingUser != null) {
            model.addAttribute("radiologyrequests", existingUser);
            System.out.println("radiologistregisterd: " + existingUser.toString());
            return "radiologistonreq"; // Assuming "radiologistreq" is your view name for displaying surgeon requests
        } else {
            System.out.println("No pending radiologist requests found");
            return "nodata"; // Assuming "nodata" is your view name for no data found scenario
        }
    }

    
    
    
    
    
    
    
	@PostMapping("/radilogistregister")
	public String seii(@RequestParam("name") String name, @RequestParam("image") MultipartFile Image,
		 @RequestParam("email") String Email,
		 @RequestParam("rid") String rid,
			@RequestParam("password") String password, 
		
			@RequestParam("contact") String contact, 
		 Model model) {
		// Create a new Dress object

		radiologistentity ob = new radiologistentity();
		
		

		ob.setName(name);
		ob.setStatus("request");
		ob.setRid(rid);
		ob.setEmail(Email);
		ob.setCatagory("Radilogist");
		
		ob.setPassword(password);
		ob.setContact(contact);
		
			
		
		
		
		boolean a = radiologyservices.checkEmail(Email);

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

			radilologistrepo.save(ob);

			return "radiologistreg";

		} else {

			return "error";

		}

		
	
	}
	
	
	 @GetMapping("/radiologistonreq/{id}")
	    public String updateStatus(@PathVariable Long id,
	                               @RequestParam("status") String status,
	                               Model model) {

		 radiologistentity radiologistentity = radiologyservices.getradiologyid(id);
	        if (radiologistentity != null) {
	        	radiologistentity.setStatus(status);
	            radiologyservices.updatedradiologiststatus(radiologistentity);
	        }
	        return "radiologistonreq"; // Assuming "radiologyreg" is your view name for handling surgeon requests
	    }
	

	 
    //radiologist request for approve and reject status

	 			
	 @GetMapping("/radiologyreq/approve/{id}")
	 public String approveradiology(@PathVariable Long id, Model model) {
	     radiologistentity sel = radiologyservices.getradiologyid(id);
	     if (sel != null) {
	         sel.setStatus("Approved");
	         radiologyservices.updatedradiologiststatus(sel);
	     }
	     return "radiologistonreq"; // Redirect to the surgeon request list page after updating
	 }

	 @GetMapping("/radiologyreq/reject/{id}")
	 public String rejectradiology(@PathVariable Long id, Model model) {
	     radiologistentity sel = radiologyservices.getradiologyid(id);
	     if (sel != null) {
	         sel.setStatus("Rejected");
	         radiologyservices.updatedradiologiststatus(sel);
	     }
	     return "radiologistonreq"; // Redirect to the surgeon request list page after updating
	 }


	 
	 @GetMapping("/patientreportsrequsttt")
		public String viewpatient(HttpSession session, Model model) {

			

		 List<Patiententity> existingUser = s2.findAllByStatus("update");


			if (existingUser != null) {
				
				model.addAttribute("patientreports", existingUser);
				System.out.println(" userappointment  " + existingUser.toString());

				return "rpatientreportsrequst";
				
			
				
			}
			  else {
			        System.out.println("No user found with status 'request'");
			        return "nodata"; // Return a view indicating no data found
			    }
			
			}
		
	 
	 
	 
	 
	 
		
	 @GetMapping("/rpopupredirect")
	    public String popupRedirect(@RequestParam("id") Long id,
	                                @RequestParam("patientid") String patientId,
	                                @RequestParam("email") String email,
	                                @RequestParam("msg") String msg,
	                              
	                                @RequestParam("docId") String docId,
	                              
	                                Model model) {
	        model.addAttribute("id", id);
	        model.addAttribute("patientId", patientId);
	        model.addAttribute("email", email);
	        model.addAttribute("msg", msg);
	       
	        model.addAttribute("docId", docId);
	    
	        return "rsurgeonpopup";
	    }
	
		@PostMapping("/rkeysubmitted")
	    public String uploadMultipleFiles(
	    		 @RequestParam("skey") String skey,
	             @RequestParam("pid") String pid,
	             @RequestParam("pemail") String pemail,
	             @RequestParam("msg") String msg,    @RequestParam("docid") String docid,  @RequestParam("sid") String sid, HttpSession session) {
	  
	System.out.println(skey);
			
			if (skey.equals("R123")) {
				
			
			sdocrequet k= new sdocrequet();
			k.setPid(pid);
			
			k.setPemail(pemail);
			k.setDocid(docid);
			k.setMsg(msg);
			k.setSid(sid);
			
			k.setDepartment("radiology");
			
			k.setStatus1("request");
			k.setStatus2("request");
			
			sdocrequet kk = prepo.save(k);
		
			 return "rpatientreportsrequst";
			
			
			} else {
				 return "no data";
			}
			
		
			
			
			
	       
	    }


	 
		/*
		 * @GetMapping("/udocstatus") public String docstatus(HttpSession session, Model
		 * model) { String pid = session.getAttribute("uid").toString();
		 * System.out.println("User ID: " + pid);
		 * 
		 * List<sdocrequet> userRequests = ud.findByPidAndStatus1(pid, "Approved");
		 * 
		 * if (!userRequests.isEmpty()) { model.addAttribute("requests", userRequests);
		 * System.out.println("User requests: " + userRequests); return "usa"; // The
		 * view where you display the requests } else {
		 * System.out.println("No user requests found with status 'Approved'"); return
		 * "nodata"; // The view for no data found } }
		 */
	 
	 
	 

		@GetMapping("/rdocstatus")
		public String docstatus(HttpSession session, Model model) {

		String sid =session.getAttribute("sid").toString();

			System.out.println(sid);
	
		
			List<sdocrequet> existingUser = prepo.findBySidAndStatus1AndDepartmentIn(sid, "Approved", Arrays.asList("radiology"));
			if (existingUser != null) {
				
				model.addAttribute("as", existingUser);
				System.out.println(" userappointment  " + existingUser.toString());

				return "rsa";
				
			
				
			}
			  else {
			        System.out.println("No user found with status 'request'");
			        return "nodata"; // Return a view indicating no data found
			    }
			
			}
		
	 
	 
	 

		 @GetMapping("/rpopup2")
		    public String popup2(@RequestParam("id") Long id,
		    		@RequestParam("key") String key,
		    		@RequestParam("sid") String sid,
		                                Model model) {
		        model.addAttribute("id", id);
		        model.addAttribute("sid", sid);
			    
		        model.addAttribute("key", key);
		    
		        return "rpopup2";
		    }
		
		 @PostMapping("/rdecryptkeysubmitted")
		    public ResponseEntity<Resource> download(
		            @RequestParam("decryptkey") String decryptkey,
		            @RequestParam("id") String id,
		            @RequestParam("sid") String sid,
		            @RequestParam("key") String key,
		            Model model) throws Exception {
			 String AES = "AES";
			 String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
			 
			 KeyGenerator keyGen = null;
				keyGen = KeyGenerator.getInstance(AES);
			 String directory = "src/main/resources/static/images/";
		    
		        // Check if the decryptkey matches the key
		        if (decryptkey.equals(key)) {
		            // Retrieve the PDF data from the service
		         
		        	System.out.println(id);
		        	System.out.println(sid);
		        	
		        	System.out.println(key);
		        	
		        	
		      	  Optional<Patiententity> optionalProduct = preportservice.getdocById(id);
		 	         if (optionalProduct.isPresent()) {
		 	             Patiententity product = optionalProduct.get();
		 	           
		 	             ByteArrayResource resource = new ByteArrayResource(product.getData());
		 	             String pdfData = product.getDocType();
		 	           
		 	             System.out.println(pdfData);
//		 	             keyGen.init(12889898);
//		 	             SecretKey secretKey = keyGen.generateKey();
//		 	          String filePath= AESUtil.decrypt(decryptkey,secretKey);


		 	             
		 	             
		 	       
		 	             
		 	             
		 	             //	             
//		 	             KeyGenerator keyGen = null;
//		 				try {
//		 					keyGen = KeyGenerator.getInstance(AES);
//		 		
//		 				
//		 				} catch (NoSuchAlgorithmException e) {
//		 					// TODO Auto-generated catch block
//		 					e.printStackTrace();
//		 				}
//		 	             keyGen.init(128);
//		 	             SecretKey secretKey = keyGen.generateKey();
//		 	          String filePath= AESUtil.decrypt(decryptkey,secretKey);

		 			    
		 	             String   filePath   = directory + pdfData;

		 		
		 			        File file = new File(filePath);

		 			 
		 			        if (file.exists()) {
		 			         
		 			            HttpHeaders headers = new HttpHeaders();
		 			            headers.setContentType(MediaType.APPLICATION_PDF);

		 			          
		 			            return ResponseEntity.ok()
		 			                    .headers(headers)
		 			                    .body(new FileSystemResource(file));
		 			        } else {
		 			         
		 			            return ResponseEntity.notFound().build();
		 			        }
		 	     } else {
		 	     
		 	         System.out.println("Invalid decryption key.");
		 	         return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		 	     }
		 	 }
		 		return null;}

	 
	 
	 
	 
	 
	 
}
