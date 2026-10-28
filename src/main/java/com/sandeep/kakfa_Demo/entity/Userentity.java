package com.sandeep.kakfa_Demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="app-user")
@Data
public class Userentity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userSeqGenerator")
	@SequenceGenerator(name = "userSeqGenerator",sequenceName = "userSeq",allocationSize = 1)
	@JsonIgnore
	private Long id;
	@Size(min = 2,max=30)
	private String firstname;
	
	@Size(min = 2,max = 30)
	private String lastname;
	
	@Email(message = "Please enter valid email")
	private String email;
	
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
	    message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=!)"
	)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
}
