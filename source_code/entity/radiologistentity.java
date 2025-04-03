package com.spring.graph.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class radiologistentity {
	
	
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String rid;
    private String email;
    private String password;
 
    private String contact;
    private String image;
    // Default constructor
	
    private String catagory;
    private String status;
	
    
    
    public radiologistentity() {
		
	}
	
    public radiologistentity(long id, String name,String rid, String email, String password, String contact, String image,
			String catagory, String status) {
		super();
		this.id = id;
		this.name = name;
		this.name = rid;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.image = image;
		this.catagory = catagory;
		this.status = status;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCatagory() {
		return catagory;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
   
    
    
    
    
    
    
    
	@Override
    public String toString() {
        return "surgeonentity [id=" + id + ", name=" + name + ",rid= "+rid+", email=" + email + ", password=" + password + ", catagory=" + catagory + ", contact=" + contact +   ", image=" + image +
        		", status=" + status + "]";
    }
    
    
	
	
	
	
	
	
	
	
	
	
	

}
