package com.spring.graph.api.controller;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.graph.api.entity.Patiententity;
import com.spring.graph.api.repository.PatientReportRepository;
import com.spring.graph.api.services.PatientReportService;

import jakarta.servlet.http.HttpSession;


@org.springframework.stereotype.Controller
public class PatientReportController {

	@Autowired
	private PatientReportService PatientReportService;

	@Autowired
	private PatientReportRepository PatientReportRepository;

	/*
	 * @PostMapping("/submitpatinetreport") public String handlepatients(
	 * 
	 * @RequestParam("patientid") String patientId,
	 * 
	 * @RequestParam("patientemail") String patientEmail,
	 * 
	 * @RequestParam("patientmsg") String patientMessage,
	 * 
	 * @RequestParam("File") MultipartFile File , Model model ) {
	 * 
	 * 
	 * 
	 * Patiententity ob = new Patiententity();
	 * 
	 * ob.setPatientid(patientId); ob.setStatus("request");
	 * ob.setEmail(patientEmail); ob.setMsg(patientMessage);
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * boolean a = PatientReportService.checkEmail(patientEmail);
	 * 
	 * String message = (a == true) ? "This Mail was Already Exist.." :
	 * "Register successfully.."; model.addAttribute("msg" , message);
	 * 
	 * 
	 * 
	 * if (a == false) {
	 * 
	 * try { // Save the dress image String fileName = ((MultipartFile)
	 * File).getOriginalFilename(); byte[] imageBytes = ((MultipartFile)
	 * File).getBytes();
	 * 
	 * // Specify the directory where the image will be saved String directory =
	 * "src/main/resources/static/images/";
	 * 
	 * // Create the directory if it doesn't exist
	 * Files.createDirectories(Paths.get(directory));
	 * 
	 * // Specify the image file path Path imagePath = Paths.get(directory +
	 * fileName);
	 * 
	 * // Write the image bytes to the specified path Files.write(imagePath,
	 * imageBytes);
	 * 
	 * ob.setFile(fileName); } catch (IOException e) { // Handle the exception
	 * (e.g., log the error, show an error message) e.printStackTrace(); }
	 * 
	 * PatientReportRepository.save(ob);
	 * 
	 * return "patientreportsupdated";
	 * 
	 * } else {
	 * 
	 * return "error";
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	@PostMapping("/submitpatinetreport")
	public String handlePatientReport (
			@RequestParam("files") MultipartFile[] files,
			@RequestParam("patientid") String patientId,
			@RequestParam("patientemail") String patientEmail,
			@RequestParam("patientmsg") String patientMessage,
			@RequestParam("docId") String docid,
			HttpSession session) {
		
	
		String directory = "src/main/resources/static/images/";

	    try {
	        // Create the directory if it doesn't exist
	        Files.createDirectories(Paths.get(directory));

	        // Loop through each file
	        for (MultipartFile file : files) {
	            // Get the original filename
	            String fileName = file.getOriginalFilename();

	            // Get the image bytes
	            byte[] imageBytes = file.getBytes();

	            // Specify the image file path
	            Path imagePath = Paths.get(directory + fileName);

	            // Write the image bytes to the specified path
	            Files.write(imagePath, imageBytes);

	            // Optional: Set the image filename in your object if needed
	            // ob.setImage(fileName); // Uncomment and replace 'ob' if needed

	            // Save the file details using your service
	            Patiententity add = PatientReportService.saveFile(file, patientId, patientEmail, patientMessage, docid,fileName);

	            // Set the saved entity in the session
	            session.setAttribute("prods", add);
	        }
	    } catch (IOException e) {
	        // Handle any IOException that might occur during file operations
	        e.printStackTrace();
	        // Optionally, handle errors by setting an error message in the session or redirecting
	        session.setAttribute("error", "Failed to save files. Please try again.");
	        return "errorPage"; // Return an error page if needed
	    }

	    return "patientreportsupdated";
	}
	

}
