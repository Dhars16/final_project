package com.spring.graph.api.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class sdocrequet {

	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String pemail;
	private String pid;
	private String docid;
	private String msg;
	private String sid;
	private String department;
	private String status1;
	
	private String status2;

	public sdocrequet() {
		
	}

	public sdocrequet(Long id, String pemail, String pid, String docid, String msg, String sid, String department,
			String status1, String status2) {
		super();
		this.id = id;
		this.pemail = pemail;
		this.pid = pid;
		this.docid = docid;
		this.msg = msg;
		this.sid = sid;
		this.department = department;
		this.status1 = status1;
		this.status2 = status2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPemail() {
		return pemail;
	}

	public void setPemail(String pemail) {
		this.pemail = pemail;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
