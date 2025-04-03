package com.spring.graph.api.controller;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.catalina.webresources.FileResource;
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

import com.spring.graph.api.algorithms.AESUtil;
import com.spring.graph.api.entity.Patiententity;
import com.spring.graph.api.entity.User;
import com.spring.graph.api.entity.sdocrequet;
import com.spring.graph.api.entity.surgeonentity;
import com.spring.graph.api.repository.Surgeon2;
import com.spring.graph.api.repository.Userrepo;
import com.spring.graph.api.repository.patientdocrepo;
import com.spring.graph.api.repository.radiologistrepo;
import com.spring.graph.api.repository.udownload;
import com.spring.graph.api.services.PatientReportService;
import com.spring.graph.api.services.Usereg;
import com.spring.graph.api.services.radiologistservices;

import jakarta.servlet.http.HttpSession;


@org.springframework.stereotype.Controller
public class Usercontroll {

	@Autowired
	private Userrepo userrepo;
	  @Autowired
	    private Surgeon2 s2;

	  @Autowired
		private udownload ud;
	  
	  
	@Autowired
	private radiologistrepo radilologistrepo;
	
	
	  @Autowired
	    private PatientReportService preportservice;
	    
	@Autowired
	private radiologistservices radiologyservices;
	  @Autowired
	    private patientdocrepo prepo;
	@Autowired
	private Usereg serdetails;
	
	@GetMapping("/ulogin")
	public String userreglogin() {

		return "userreg";
	}
	

	
    @PostMapping("/userreg")
    public String register(User us) {
    //UserDtls user = new UserDtls(); 
    	  // Your registration logic here
    	us.setStatus("request");
    	    	
    	serdetails.saveUsers(us);
	return "userreg";

    }
    

    @GetMapping("/urequest")
    public String viewusers(HttpSession session, Model model) {
    	 List<User> existingUser = userrepo.findByStatus("request");

        if (existingUser != null) {
            model.addAttribute("userrequest", existingUser);
            System.out.println("User appointment: " + existingUser.toString());
            
            return "userrequest";
        } 
        else {
        	// Handle case where no data is found
            System.out.println("No user found with status 'request'");
            return "nodata";
        }
    }

	
 @PostMapping("/uslogin")
 public String register(User us, HttpSession session) {
     // Perform authentication logic here
     User authenticatedUser = serdetails.getUserByEmailAndPassword(us.getEmail(), us.getPassword());
     
     if (authenticatedUser != null) {
         // Set session attributes if needed
         session.setAttribute("loggedInUser", authenticatedUser);
         session.setAttribute("uid", authenticatedUser.getUserid());
       
         session.setAttribute("cc", authenticatedUser.getContact());
         // Redirect to another page upon successful login
         return "userinterface"; // Replace with your desired URL
     } else {
         // Handle login failure
         return "login"; // Redirect back to login page with error message
     }
 }


    

    @GetMapping("/userrequest/{id}")
	public String updateStatus(@PathVariable Long id, Model model) {

		User sel = serdetails.getuserid(id);
		  if (sel != null) {
	            sel.setStatus("Approved");
	            serdetails.updateduserstatus(sel);
	        }
	        return "userrequest";
	    }

 @GetMapping("/userrequestt/{id}")
	public String updatedStatus(@PathVariable Long id, Model model) {
 
		User sel = serdetails.getuserid(id);
		  if (sel != null) {
	            sel.setStatus("rejected");
	            serdetails.updateduserstatus(sel);
	        }
	        return "userrequest";
	    }
				
 @GetMapping("/upatientreportsrequst")
 public String viewPatient(HttpSession session, Model model) {
     // Retrieve the patient ID from the session
     String id = (String) session.getAttribute("uid"); // Ensure 'id' matches the session attribute name

     if (id == null) {
         // If 'id' is null, handle the error (e.g., redirect to an error page or show a message)
         System.out.println("No patient ID found in session.");
         return "error"; // Return a view indicating an error or redirect to another page
     }

     System.out.println("Patient ID: " + id);

     // Retrieve the list of patient reports by status and patient ID
     List<Patiententity> patientReports = s2.findByStatusAndPatientid("update", id);

     // Add the patient reports to the model, whether empty or not
     model.addAttribute("patientreports", patientReports);
     System.out.println("User appointments: " + patientReports);

     // Return the view with the data (empty or populated)
     return "upatientreportsrequst";
 }

 
	
 @GetMapping("/upopupredirect")
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
    
        return "upr";
    }

	@PostMapping("/ukeysubmitted")
    public String uploadMultipleFiles(
    		 @RequestParam("skey") String skey,
             @RequestParam("pid") String pid,
             @RequestParam("pemail") String pemail,
             @RequestParam("msg") String msg,    @RequestParam("docid") String docid,  @RequestParam("sid") String sid, HttpSession session) {
  
System.out.println(skey);
		
		if (skey.equals(pid)) {
			
		
		sdocrequet k= new sdocrequet();
		k.setPid(pid);
		
		k.setPemail(pemail);
		k.setDocid(docid);
		k.setMsg(msg);
		k.setSid(sid);
		
		k.setDepartment("user");
		
		k.setStatus1("request");
		k.setStatus2("request");
		
		sdocrequet kk = prepo.save(k);
	
		 return "upatientreportsrequst";
		
		
		} else {
			 return "no data";
		}
		
	
		
		
		
       
    }


 
 
 
 
 

	@GetMapping("/udocstatus")
	public String docstatus(HttpSession session, Model model) {
	    String pid = session.getAttribute("uid").toString();
	    System.out.println("User ID: " + pid);

	    // Fetch records where department is either "User" or "Radiology"
	    List<sdocrequet> userRequests = ud.findByPidAndStatus1AndDepartmentIn(pid, "Approved", Arrays.asList("user"));

	    if (!userRequests.isEmpty()) {
	        model.addAttribute("requests", userRequests);
	        System.out.println("User & Radiology requests: " + userRequests);
	        return "usa"; // The view where you display the requests
	    } else {
	        System.out.println("No user requests found with status 'Approved'");
	        return "nodata"; // The view for no data found
	    }
	}


 
 

	 @GetMapping("/upopup2")
	    public String popup2(@RequestParam("id") Long id,
	    		@RequestParam("key") String key,
	    		@RequestParam("sid") String sid,
	                                Model model) {
	        model.addAttribute("id", id);
	        model.addAttribute("sid", sid);
		    
	        model.addAttribute("key", key);
	    
	        return "upopup2";
	 }
	 
	 
	
	 @PostMapping("/udecryptkeysubmitted")
	 public ResponseEntity<FileSystemResource> download(
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
	    
	     if (decryptkey.equals(key)) {
	         
	         Optional<Patiententity> optionalProduct = preportservice.getdocById(id);
	         if (optionalProduct.isPresent()) {
	             Patiententity product = optionalProduct.get();
	           
	             ByteArrayResource resource = new ByteArrayResource(product.getData());
	             String pdfData = product.getDocType();
	           
	             System.out.println(pdfData);
//	             keyGen.init(12889898);
//	             SecretKey secretKey = keyGen.generateKey();
//	          String filePath= AESUtil.decrypt(decryptkey,secretKey);


	             
	             
	       
	             
	             
	             //	             
//	             KeyGenerator keyGen = null;
//				try {
//					keyGen = KeyGenerator.getInstance(AES);
//		
//				
//				} catch (NoSuchAlgorithmException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	             keyGen.init(128);
//	             SecretKey secretKey = keyGen.generateKey();
//	          String filePath= AESUtil.decrypt(decryptkey,secretKey);

			    
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
