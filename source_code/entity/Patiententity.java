//package com.spring.graph.api.entity;
//
//
//
//
//
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Lob;
//
//
//
//@Entity
//public class Patiententity {
//	
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String patientid;
//    private String email;
//    private String msg;
//    private String docName;
//    private String docType;
//    private String docId;
//    private String encryptData;
//    @Lob
//	private byte[] data;
//
//	  private String status;
//
//	  
//	 
//	  
//	  
//	  
//	public Patiententity() {
//		
//	}
//
//
//
//
//
//
//	public Patiententity(Long id, String patientid, String email, String msg, String docName, String docType,
//			String docId, String encryptData, byte[] data, String status) {
//		super();
//		this.id = id;
//		this.patientid = patientid;
//		this.email = email;
//		this.msg = msg;
//		this.docName = docName;
//		this.docType = docType;
//		this.docId = docId;
//		this.encryptData = encryptData;
//		this.data = data;
//		this.status = status;
//	}
//
//
//
//
//
//
//	public Long getId() {
//		return id;
//	}
//
//
//
//
//
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//
//
//
//
//
//	public String getPatientid() {
//		return patientid;
//	}
//
//
//
//
//
//
//	public void setPatientid(String patientid) {
//		this.patientid = patientid;
//	}
//
//
//
//
//
//
//	public String getEmail() {
//		return email;
//	}
//
//
//
//
//
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//
//
//
//
//
//	public String getMsg() {
//		return msg;
//	}
//
//
//
//
//
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//
//
//
//
//
//
//	public String getDocName() {
//		return docName;
//	}
//
//
//
//
//
//
//	public void setDocName(String docName) {
//		this.docName = docName;
//	}
//
//
//
//
//
//
//	public String getDocType() {
//		return docType;
//	}
//
//
//
//
//
//
//	public void setDocType(String docType) {
//		this.docType = docType;
//	}
//
//
//
//
//
//
//	public String getDocId() {
//		return docId;
//	}
//
//
//
//
//
//
//	public void setDocId(String docId) {
//		this.docId = docId;
//	}
//
//
//
//
//
//
//	public String getEncryptData() {
//		return encryptData;
//	}
//
//
//
//
//
//
//	public void setEncryptData(String encryptData) {
//		this.encryptData = encryptData;
//	}
//
//
//
//
//
//
//	public byte[] getData() {
//		return data;
//	}
//
//
//
//
//
//
//	public void setData(byte[] data) {
//		this.data = data;
//	}
//
//
//
//
//
//
//	public String getStatus() {
//		return status;
//	}
//
//
//
//
//
//
//	public void setStatus(String status) {
//		this.status = status;
//	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//}

package com.spring.graph.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Patiententity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientid;
    private String email;
    private String msg;
    private String docName;
    private String docType;
    private String docId;
    private String encryptData;
    @Lob
    @Column(name = "phash", columnDefinition = "LONGTEXT")
    private String phash;

    @Lob
    @Column(name = "ahash", columnDefinition = "LONGTEXT")
    private String ahash;
    
    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    private String status;

    // Default constructor
    public Patiententity() {}

    // Parameterized constructor
      
    // Getters and Setters
    public Long getId() { return id; }
    public Patiententity(Long id, String patientid, String email, String msg, String docName, String docType,
			String docId, String encryptData, String phash, String ahash, byte[] data, String status) {
		super();
		this.id = id;
		this.patientid = patientid;
		this.email = email;
		this.msg = msg;
		this.docName = docName;
		this.docType = docType;
		this.docId = docId;
		this.encryptData = encryptData;
		this.phash = phash;
		this.ahash = ahash;
		this.data = data;
		this.status = status;
	}

	public void setId(Long id) { this.id = id; }
    public String getPatientid() { return patientid; }
    public void setPatientid(String patientid) { this.patientid = patientid; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public String getDocName() { return docName; }
    public void setDocName(String docName) { this.docName = docName; }
    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }
    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }
    public String getEncryptData() { return encryptData; }
    public void setEncryptData(String encryptData) { this.encryptData = encryptData; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

	public String getPhash() {
		return phash;
	}

	public void setPhash(String phash) {
		this.phash = phash;
	}

	public String getAhash() {
		return ahash;
	}

	public void setAhash(String ahash) {
		this.ahash = ahash;
	}








}
