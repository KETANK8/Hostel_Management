package com.HostelMS.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	
	// Using Regular Expression
	// RegEx To Check Validation
	// Giving Condition to Set Unique UserName
	@NotNull
	@Pattern(regexp="[a-zA-Z]{5,}",message ="\nUserName should Atleast have Five Characters.")
	private String userName;
	
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	
	// Using Regular Expression
	// RegEx To Check Validation	
	// Giving Condition to Set Contact Number
	@NotNull
	@Pattern(regexp="[0-9]{9,}",message ="\nContact No should Atleast have 10 digits.")
	private String userContact;

	// Using Regular Expression
	// RegEx To Check Validation	
	// Giving Condition to Set Unique Password
	@NotNull
	@Pattern(regexp="[a-zA-Z0-9@#]{6,}",message ="\nPassword must be AlphaNumeric and Atleast have 8 Characters.")
	private String userPassword;
	private String userAddress;
	private String userRole;
	private int userRent;
	
	@ManyToOne
	private Room userRoom;
}
