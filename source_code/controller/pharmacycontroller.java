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
import com.spring.graph.api.entity.pharmacyentity;
import com.spring.graph.api.entity.sdocrequet;
import com.spring.graph.api.repository.Surgeon2;
import com.spring.graph.api.repository.patientdocrepo;
import com.spring.graph.api.repository.pharmacyrepo;
import com.spring.graph.api.repository.radiologistrepo;
import com.spring.graph.api.services.PatientReportService;
import com.spring.graph.api.services.pharmacyservice;

import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class pharmacycontroller {	
	

	
	  @Autowired
	    private Surgeon2 s2;

	@Autowired
	private radiologistrepo radilologistrepo;
	
	 @Autowired
	    private patientdocrepo prepo;
	    
	
	  @Autowired
	    private PatientReportService preportservice;
	
	
	
	
	@Autowired
	private pharmacyrepo pharmacyrepo;
	
	@Autowired
	private pharmacyservice pharmacyservice;
	
	
	@GetMapping("/Pharmacyreglogin")
	public String reglogin() {

		return "pharmacyreg";
	}
	
	
	//pharmnacy login logics
	
	 @PostMapping("/pharmacylogin")
	   public String login(pharmacyentity sell, Model model , HttpSession session) {
	   //UserDtls user = new UserDtls(); 
	  
		   System.out.println(sell.getEmail());
		   System.out.println(sell.getPassword());
		   
		   pharmacyentity st = pharmacyservice.getdocByEmailAndPassword(sell.getEmail(), sell.getPassword());
	   if (st != null) {
	       // Session attributes are already set in UserService
		   session.setAttribute("sid", st.getPid());
		  	   
		   System.out.println(sell.getEmail());
		
	       // Add email attribute to the model
			
		   return "pharmacyinterface";

	   } else {
		   
		   
		   System.out.println(st);
		   
		   return "pharmacyreg";

	 }
	   
	   }
    
// for refernce login page	
//	@PostMapping("/pharmacylogin")
//	public String login(@RequestParam("email") String email,
//	                    @RequestParam("password") String password,
//	                    HttpSession session,
//	                    Model model) {
//	    // Check if email and password are provided
//	    if (email.isEmpty() || password.isEmpty()) {
//	        model.addAttribute("error", "Please provide email and password");
//	        return "pharmacyreg"; // Redirect to registration page with error message
//	    }
//
//	    // Attempt to fetch pharmacy entity from database
//	    pharmacyentity pharmacy = pharmacyservice.getdocByEmailAndPassword(email, password);
//
//	    if (pharmacy != null && pharmacy.getStatus().equalsIgnoreCase("Approved")) {
//	        // Set session attributes for authenticated user
//	        session.setAttribute("pharmacyId", pharmacy.getId());
//	        session.setAttribute("pharmacyEmail", pharmacy.getEmail());
//	        session.setAttribute("pharmacyName", pharmacy.getName());
//
//	        // Redirect to pharmacyinterface if status is approved
//	        return "pharmacyinterface";
//	    } else {
//	        // If pharmacy is not approved or not found, redirect to registration page with error message
//	        model.addAttribute("error", "Invalid credentials or pharmacy is not approved");
//	        return "pharmacyreg";
//	    }
//	}

	

    //consider like if pharmacy register we getting all the details from frontend html page name to we process
	@PostMapping("/pharmacyregister")
	public String seii(@RequestParam("name") String name, @RequestParam("image") MultipartFile Image,
		 @RequestParam("email") String Email,
		 @RequestParam("pid") String pid,
			@RequestParam("password") String password, 
		
			@RequestParam("contact") String contact, 
		 Model model) {
		// Create a new  object

		pharmacyentity ob = new pharmacyentity();
		
		

		ob.setName(name);
		ob.setPid(pid);
		ob.setStatus("request");
		ob.setEmail(Email);
		ob.setCatagory("pharmacyist");
		
		ob.setPassword(password);
		ob.setContact(contact);
		
				
		boolean a = pharmacyservice.checkEmail(Email);

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

			pharmacyrepo.save(ob);

			return "pharmacyreg";//after registered it returning same register and datas all sended it to db with equallent name

		} else {

			return "error";

		}	

	}
	
	
	//here prequest is coming from adminhome 
//    @GetMapping("/prequest")
//    public String viewpharmacy(HttpSession session, Model model) {
//        List<pharmacyentity> existingUser = pharmacyrepo.findByStatus("request");
//        
//
//        if (existingUser != null) {
//            model.addAttribute("pharmacyrequests", existingUser);
//            System.out.println("pharmacyregistered: " + existingUser.toString());
//            return "pharmacyonreq"; // Assuming "pharmnacyreg" is your view name for displaying surgeon requests
//        } else {
//            System.out.println("No user found with status 'request'");
//            return "nodata"; // Assuming "nodata" is your view name for no data found scenario
//        }
//    }
//	
	@GetMapping("/prequest")
	public String viewpharmacy(HttpSession session, Model model) {
	    List<pharmacyentity> existingUsers = pharmacyrepo.findByStatus("request");

		/*
		 * if (!existingUsers.isEmpty()) { model.addAttribute("pharmacyrequests",
		 * existingUsers); return "pharmacyonreq"; // Return the view with pharmacy
		 * requests filtered by status } else {
		 * System.out.println("No pharmacy requests found"); return "nodata"; // Handle
		 * case where no requests are found }
		 */
	    
	    if (existingUsers != null) {
            model.addAttribute("pharmacyrequests", existingUsers);
            System.out.println("surgeonregisterd: " + existingUsers.toString());
            return "pharmacyonreq"; // Assuming "surgeonreq" is your view name for displaying surgeon requests
        } else {
            System.out.println("No user found with status 'request'");
            return "nodata"; // Assuming "nodata" is your view name for no data found scenario
        }
	}

	
	
	 @GetMapping("/pharmacyonreq/{id}")
	    public String updateStatus(@PathVariable Long id,
	                               @RequestParam("status") String status,
	                               Model model) {

		 pharmacyentity pharmacy = pharmacyservice.getpharmacyid(id);
	        if (pharmacy != null) {
	        	pharmacy.setStatus(status);
	            pharmacyservice.updatedpharmacystatus(pharmacy);
	        }
	        return "pharmacyonreq"; // Assuming "pharmacyonreq" is your view name for handling surgeon requests
	    }
	    
	    

	
	 //pharmacy request for approve and reject status

		
	 @GetMapping("/pharmacyonreq/approve/{id}")
	 public String approvepharmacyian(@PathVariable Long id, Model model) {
		 pharmacyentity sel = pharmacyservice.getpharmacyid(id);
	     if (sel != null) {
	         sel.setStatus("Approved");
	         pharmacyservice.updatedpharmacystatus(sel);
	     }
	     return "pharmacyonreq"; // Redirect to the surgeon request list page after updating
	 }

	 @GetMapping("/pharmacyonreq/reject/{id}")
	 public String rejectpharmacyian(@PathVariable Long id, Model model) {
		 pharmacyentity sel = pharmacyservice.getpharmacyid(id);
	     if (sel != null) {
	         sel.setStatus("Rejected");
	         pharmacyservice.updatedpharmacystatus(sel);
	     }
	     return "pharmacyonreq"; // Redirect to the pharmacy request list page after updating
	 }

	
	
	
	 @GetMapping("/ppatientreportsrequst")
		public String viewpatient(HttpSession session, Model model) {

			

		 List<Patiententity> existingUser = s2.findAllByStatus("update");


			if (existingUser != null) {
				
				model.addAttribute("patientreports", existingUser);
				System.out.println(" userappointment  " + existingUser.toString());

				return "ppatientreportsrequst";
				
			
				
			}
			  else {
			        System.out.println("No user found with status 'request'");
			        return "nodata"; // Return a view indicating no data found
			    }
			
			}
		
	 
	 
	 
	 
	 
		
	 @GetMapping("/ppopupredirect")
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
	    
	        return "psurgeonpopup";
	    }
	
		@PostMapping("/pkeysubmitted")
	    public String uploadMultipleFiles(
	    		 @RequestParam("skey") String skey,
	             @RequestParam("pid") String pid,
	             @RequestParam("pemail") String pemail,
	             @RequestParam("msg") String msg,    @RequestParam("docid") String docid,  @RequestParam("sid") String sid, HttpSession session) {
	  
	System.out.println(skey);
			
			if (skey.equals("P123")) {
				
			
			sdocrequet k= new sdocrequet();
			k.setPid(pid);
			
			k.setPemail(pemail);
			k.setDocid(docid);
			k.setMsg(msg);
			k.setSid(sid);
			
			k.setDepartment("pharmacy");
			
			k.setStatus1("request");
			k.setStatus2("request");
			
			sdocrequet kk = prepo.save(k);
		
			 return "ppatientreportsrequst";
			
			
			} else {
				 return "no data";
			}
			
		
			
			
			
	       
	    }


	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

		@GetMapping("/pdocstatus")
		public String docstatus(HttpSession session, Model model) {

		String sid =	session.getAttribute("sid").toString();

			System.out.println(sid);
	
		
			List<sdocrequet> existingUser = prepo.findBySidAndStatus1AndDepartmentIn(sid, "Approved", Arrays.asList("Pharmacy"));

			if (existingUser != null) {
				
				model.addAttribute("as", existingUser);
				System.out.println(" userappointment  " + existingUser.toString());

				return "psa";
				
			
				
			}
			  else {
			        System.out.println("No user found with status 'request'");
			        return "nodata"; // Return a view indicating no data found
			    }
			
			}
		
	 
	 
	 

		 @GetMapping("/pppopup2")
		    public String popup2(@RequestParam("id") Long id,
		    		@RequestParam("key") String key,
		    		@RequestParam("sid") String sid,
		                                Model model) {
		        model.addAttribute("id", id);
		        model.addAttribute("sid", sid);
			    
		        model.addAttribute("key", key);
		    
		        return "ppopup2";
		    }
		
		 @PostMapping("/pdecryptkeysubmitted")
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
