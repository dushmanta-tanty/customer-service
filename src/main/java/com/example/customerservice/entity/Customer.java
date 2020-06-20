package com.example.customerservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bkm_customers")
public class Customer {

	@Id
    @GeneratedValue
    private Long id;
     
    @Column(name="first_name", nullable=false)
    private String firstName;
     
    @Column(name="last_name", nullable=false)
    private String lastName;
     
    @Column(name="email", nullable=false, length=100)
    private String email;
    
    @Column(name="dob", nullable=false)
    private Date dob;
    
    @Column(name="password", nullable=false)
    private String password;
	public Long getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public Date getDob() {
		return dob;
	}
	public String getPassword() {
		return password;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
