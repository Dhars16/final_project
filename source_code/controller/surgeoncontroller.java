
package com.spring.graph.api.controller;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.spring.graph.api.entity.Patiententity;
import com.spring.graph.api.entity.sdocrequet;
import com.spring.graph.api.entity.surgeonentity;
import com.spring.graph.api.repository.Surgeon2;
import com.spring.graph.api.repository.patientdocrepo;
import com.spring.graph.api.repository.surgeonrepo;
import com.spring.graph.api.services.PatientReportService;
import com.spring.graph.api.services.patientdocservice;
import com.spring.graph.api.services.surgeonservices;

import jakarta.servlet.http.HttpSession;

@Controller
public class surgeoncontroller {

    @Autowired
    private surgeonrepo surgeonRepo;
    @Autowired
    private Surgeon2 s2;

    @Autowired
    private patientdocrepo prepo;
    
    @Autowired
    private PatientReportService preportservice;
    
    @Autowired
    private patientdocservice pservice;
    
    @Autowired
    private surgeonservices surgeonService;

    @GetMapping("/surgeonreglogin")
    public String reglogin() {
        return "surgeonreg"; // Assuming "surgeonreg" is your view name for surgeon registration/login
    }


	 @PostMapping("/surgeonlogin")
	   public String loginus(surgeonentity sell, Model model , HttpSession session) {
	   //UserDtls user = new UserDtls(); 
	  
		   System.out.println(sell.getEmail());
		   System.out.println(sell.getPassword());
		   
		   surgeonentity st=surgeonService.getdocByEmailAndPassword(sell.getEmail(), sell.getPassword());
	   if (st != null) {
	       // Session attributes are already set in UserService
		   session.setAttribute("sid", st.getSid());
		  	   
		   System.out.println(sell.getEmail());
		
	       // Add email attribute to the model
			
		   return "surgeoninterface";

	   } else {
		   
		   
		   System.out.println(st);
		   
		   return "surgeonreg";

	 }
	   
	   }
       
    
    
    @GetMapping("/srequest")
    public String viewSurgeons(HttpSession session, Model model) {
        List<surgeonentity> existingUser = surgeonRepo.findByStatus("request");
        

        if (existingUser != null) {
            model.addAttribute("surgeonrequests", existingUser);
            System.out.println("surgeonregisterd: " + existingUser.toString());
            return "surgeonreq"; // Assuming "surgeonreq" is your view name for displaying surgeon requests
        } else {
            System.out.println("No user found with status 'request'");
            return "nodata"; // Assuming "nodata" is your view name for no data found scenario
        }
    }

    @PostMapping("/surgeonregister")
    public String registerSurgeon(@RequestParam("name") String name,
    		                      @RequestParam("sid") String sid,
                                  @RequestParam("image") MultipartFile image,
                                  @RequestParam("email") String email,
                                  @RequestParam("password") String password,
                                  @RequestParam("contact") String contact,
                                  Model model) {

        surgeonentity surgeonEntity = new surgeonentity();
        surgeonEntity.setName(name);
        surgeonEntity.setSid(sid);
        surgeonEntity.setStatus("request");
        surgeonEntity.setEmail(email);
        surgeonEntity.setCatagory("surgeon");
        surgeonEntity.setPassword(password);
        surgeonEntity.setContact(contact);

        boolean emailExists = surgeonService.checkEmail(email);

        String message = emailExists ? "This email already exists." : "Registered successfully.";
        model.addAttribute("msg", message);

        if (!emailExists) {
            try {
                // Save the image
                String fileName = image.getOriginalFilename();
                byte[] imageBytes = image.getBytes();
                String directory = "src/main/resources/static/images/";

                // Create directories if they don't exist
                Files.createDirectories(Paths.get(directory));

                // Specify the image file path
                Path imagePath = Paths.get(directory + fileName);

                // Write the image bytes to the specified path
                Files.write(imagePath, imageBytes);

                surgeonEntity.setImage(fileName);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception as needed
            }

            surgeonRepo.save(surgeonEntity);
            return "surgeonreg"; // Redirect to registration page after successful registration
        } else {
            return "error"; // Return error page or handle differently for duplicate email
        }
    }

    @GetMapping("/surgeonreq/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam("status") String status,
                               Model model) {

        surgeonentity surgeonEntity = surgeonService.getsurgeonid(id);
        if (surgeonEntity != null) {
            surgeonEntity.setStatus(status);
            surgeonService.updatedsurgeonstatus(surgeonEntity);
        }
        return "surgeonreq"; // Assuming "surgeonreq" is your view name for handling surgeon requests
    }
    
    

    
    
    //surgeon request for approve and reject status

	 			
	 @GetMapping("/surgeonreq/approve/{id}")
	 public String approveSurgeon(@PathVariable Long id, Model model) {
	     surgeonentity sel = surgeonService.getsurgeonid(id);
	     if (sel != null) {
	         sel.setStatus("Approved");
	         surgeonService.updatedsurgeonstatus(sel);
	     }
	     return "surgeonreq"; // Redirect to the surgeon request list page after updating
	 }

	 @GetMapping("/surgeonreq/reject/{id}")
	 public String rejectSurgeon(@PathVariable Long id, Model model) {
	     surgeonentity sel = surgeonService.getsurgeonid(id);
	     if (sel != null) {
	         sel.setStatus("Rejected");
	         surgeonService.updatedsurgeonstatus(sel);
	     }
	     return "surgeonreq"; // Redirect to the surgeon request list page after updating
	 }

    
    
	 
	 
	 
	 
		@GetMapping("/patientreportsrequst")
		public String viewpatient(HttpSession session, Model model) {

			

			List<Patiententity> existingUser = s2.findAllByStatus("update");

			if (existingUser != null) {
				
				model.addAttribute("patientreports", existingUser);
				System.out.println(" userappointment  " + existingUser.toString());

				return "patientreportsrequst";
				
			
				
			}
			  else {
			        System.out.println("No user found with status 'request'");
			        return "nodata"; // Return a view indicating no data found
			    }
			
			}
		
	
	 
	 
	 
		
		
		
		
		 @GetMapping("/popupredirect")
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
		    
		        return "surgeonpopup";
		    }
		
		
		
		
		
		
		
			@PostMapping("/keysubmitted")
		    public String uploadMultipleFiles(
		    		 @RequestParam("skey") String skey,
		             @RequestParam("pid") String pid,
		             @RequestParam("pemail") String pemail,
		             @RequestParam("msg") String msg,    @RequestParam("docid") String docid,  @RequestParam("sid") String sid, HttpSession session) {
		  
		System.out.println(skey);
				
				if (skey.equals("S123")) {
					
				
				sdocrequet k= new sdocrequet();
				k.setPid(pid);
				
				k.setPemail(pemail);
				k.setDocid(docid);
				k.setMsg(msg);
				k.setSid(sid);
				
				k.setDepartment("surgeon");
				
				k.setStatus1("request");
				k.setStatus2("request");
				
				sdocrequet kk = prepo.save(k);
			
				 return "patientreportsrequst";
				
				
				} else {
					 return "no data";
				}
				
			
				
				
				
		       
		    }


			
		
			@GetMapping("/docstatus")
			public String docstatus(HttpSession session, Model model) {
			    String sid = session.getAttribute("sid").toString();
			    System.out.println("FSFSF" + sid);

				List<sdocrequet> existingUser = prepo.findBySidAndStatus1AndDepartmentIn(sid, "Approved", Arrays.asList("surgeon"));

			    if (existingUser != null) {
			        model.addAttribute("as", existingUser);
			        System.out.println("user appointment " + existingUser.toString());
			        return "sa"; // Assuming sa.html exists
			    } else {
			        System.out.println("No user found with status 'Approved'");
			        model.addAttribute("message", "No user found with status 'Approved'");
			        return "errorPage"; // You can create an errorPage.html with the message
			    }
			}

			
		
		
		
			 @GetMapping("/popup2")
			    public String popup2(@RequestParam("id") Long id,
			    		@RequestParam("key") String key,
			    		@RequestParam("sid") String sid,
			                                Model model) {
			        model.addAttribute("id", id);
			        model.addAttribute("sid", sid);
				    
			        model.addAttribute("key", key);
			    
			        return "popup2";
			    }
			
			
			 @PostMapping("/decryptkeysubmitted")
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
//			 	             keyGen.init(12889898);
//			 	             SecretKey secretKey = keyGen.generateKey();
//			 	          String filePath= AESUtil.decrypt(decryptkey,secretKey);


			 	             
			 	             
			 	       
			 	             
			 	             
			 	             //	             
//			 	             KeyGenerator keyGen = null;
//			 				try {
//			 					keyGen = KeyGenerator.getInstance(AES);
//			 		
//			 				
//			 				} catch (NoSuchAlgorithmException e) {
//			 					// TODO Auto-generated catch block
//			 					e.printStackTrace();
//			 				}
//			 	             keyGen.init(128);
//			 	             SecretKey secretKey = keyGen.generateKey();
//			 	          String filePath= AESUtil.decrypt(decryptkey,secretKey);

			 			    
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

