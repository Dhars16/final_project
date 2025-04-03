package com.spring.graph.api.services;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.graph.api.algorithms.Block;
import com.spring.graph.api.algorithms.difficultylevel;
import com.spring.graph.api.algorithms.mining;
import com.spring.graph.api.encryptionservice.EncryptionService;
import com.spring.graph.api.entity.Patiententity;

import com.spring.graph.api.repository.PatientReportRepository;

@Service
public class PatientReportService {
	
	@Autowired
	private PatientReportRepository PatientReportRepository;

	@Autowired
	private EncryptionService eservice;


public Optional<Patiententity> getdocById(String id) {
    return PatientReportRepository.findByDocId(id);
}
public Patiententity saveFile(MultipartFile file, String patientId, String patientEmail, String patientMessage, String docid,String fileName) {
    String docName = file.getOriginalFilename();
    try {
        Patiententity details = new Patiententity();
        details.setPatientid(patientId);
        details.setEmail(patientEmail);
        details.setMsg(patientMessage);
        details.setDocId(docid);
        details.setDocName(docName);
        details.setDocType(fileName);
        details.setData(file.getBytes());

       
        String encryptedData = eservice.encryptData(file.getBytes());
        details.setEncryptData(encryptedData);

        

        
Block genesisBlock = new Block(encryptedData, "0");

String phas=genesisBlock.hash;

Block secondBlock = new Block("Yo im the second block",genesisBlock.hash);
System.out.println("Hash for block 2 : " + secondBlock.hash);
String aphas=secondBlock.hash; 
        
details.setPhash(phas);
        
details.setAhash(aphas);
        
       

             details.setStatus("update");

      
             int initialDifficulty = 2;
 	        mining blockchain = new mining(initialDifficulty);

 	        // Adding blocks to the blockchain
 	        blockchain.addBlock(phas);
 	        

 	        // Validating the blockchain
 	        blockchain.isChainValid();
 	
 	    	System.out.println("yyyy:"+aphas);
 	
 	    	
 	    	String difficultyTargetHex = aphas;

 	        // Convert the difficulty target to a BigInteger
 	        BigInteger difficultyTarget = new BigInteger(difficultyTargetHex, 16);
 difficultylevel dd=new difficultylevel();
 	        // Calculate the difficulty level
 	     
 	        
 	       

 	        // Print the result
 	   
 	        
 	        
 	      BigInteger referenceDifficultyTarget = new BigInteger("00000000FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16);

     // Calculate the difficulty level as a ratio of the reference difficulty target
     double difficultyRatio = referenceDifficultyTarget.doubleValue() / difficultyTarget.doubleValue();

     // Adjust the difficulty level as needed (this is a simple example)
   
 	        
 	        
 	        
 	        
 	        
 	        
 	        
 	        
 	        
 	        System.out.println("Difficulty Level: " + difficultyRatio);
 	    	   
             
             
             
             
             
             
             
             
             
             
             return PatientReportRepository.save(details); // save the entity
        
    } catch (Exception e) {
        e.printStackTrace();
        return null;
        
      
    }
    
}
  
	
	
	public boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		return PatientReportRepository.existsByEmail(email);
	}
	
}
