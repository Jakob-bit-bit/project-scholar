package com.forgotten_sprint.project_scholar.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name= "instructors")
public class Instructor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long instructorId;
	
	@NotBlank(message ="First name is required")
	@Size(max=50)
	@Column(nullable = false, length = 50)
	private String firstName;
	
	@NotBlank(message = "Last name is required ")
	@Size(max = 50)
	@Column(nullable = false, length = 50)
	private String lastName;
	
	@NotBlank(message = "Email is required")
	@Email(message= "Please enter a valid email")
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String passwordHash;
	
	@NotBlank(message = "Departement is required")
	@Size(max=100)
	@Column(nullable = false, length = 100)
	private String department;
	
	@Size(max = 50)
	private String specialization;
	
	private LocalDate hireDate;
	
	@Column(nullable = false)
	private boolean isActive = true;
	
	public Instructor( ) {
		
	}

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
