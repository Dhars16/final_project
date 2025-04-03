package com.spring.graph.api.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	

    private String name;
    private String email;
    private String password;
    private String age;
    private String contact;
    private String userid;
	private String status;//Default constructor
    
   
	
	public User(Object ob) {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public User() {
	
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








	public String getAge() {
		return age;
	}








	public void setAge(String age) {
		this.age = age;
	}








	public String getContact() {
		return contact;
	}








	public void setContact(String contact) {
		this.contact = contact;
	}








	public String getUserid() {
		return userid;
	}








	public void setUserid(String userid) {
		this.userid = userid;
	}



    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}






	public User(long id, String name, String email, String password, String age, String contact, String userid) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.age = age;
		this.contact = contact;
		this.userid = userid;
		
	}




	@Override
    public String toString() {
        return "userreg [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", age=" + age + ", contact=" + contact +   ", userid=" + userid
        		+
        		", status=" + status + "]";
    }




}
