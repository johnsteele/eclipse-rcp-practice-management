package com.steelejr.pm.core.model;

import java.util.Date;
import java.util.GregorianCalendar;


public abstract class Person {
	
	/** A person's gender. */
	public static enum Gender { MALE, FEMALE, UNKNOWN };
	
	/** Person's first name. */
	private String fFirstName;
	
	/** Person's last name. */
	private String fLastName;
	
	/** Person's middle name. */
	private String fMiddleName;
	
	/** Birth date. */
	private Date fBirthDate;
	
	/** Gender */
	private Gender fGender;
	
	/** Website User name */
	private String fUserName;
	
	/** Website password. */
	private String fPassword;
	
	/** Contact information */
	private Contact fContact;
	
	
	public Person () {
		fFirstName = "";
		fLastName  = "";
		fMiddleName = "";
		fBirthDate   = GregorianCalendar.getInstance().getTime();
		fGender    = Gender.UNKNOWN;
		fUserName  = "";
		fPassword  = "";
		fContact   = new Contact();
	}
	
	public Person (
			String firstName,
			String lastName, 
			String middleName, 
			String email, 
			int phone, 
			Date dob, 
			Gender gender,
			String userName, 
			String password,
			Contact contact) {
		
		fFirstName    = firstName;
		fLastName     = lastName;
		fMiddleName   = middleName;
		fBirthDate    = dob;
		fGender       = gender;
		fUserName     = userName;
		fPassword     = password;
		fContact      = contact;
	}
	
	
	public Person (String first, String last) {
		fFirstName = first;
		fLastName  = last;
		fMiddleName = "";
		fBirthDate   = GregorianCalendar.getInstance().getTime();
		fGender    = Gender.UNKNOWN;
		fUserName  = "";
		fPassword  = "";
		fContact   = new Contact();
	}

	/**
	 * Returns the person's last name.
	 * 
	 * @return This person's last name.
	 */
	public String getLastName() {
		return fLastName;
	}

	/**
	 * Sets this person's last name.
	 * 
	 * @param lastName The new last name.
	 */
	public void setLastName(String lastName) {
		this.fLastName = lastName;
	}

	/**
	 * Returns this person's first name.
	 * 
	 * @return This person's first name.
	 */
	public String getFirstName() {
		return fFirstName;
	}

	/**
	 * Sets this person's first name. 
	 * 
	 * @param firstName The new first name.
	 */
	public void setFirstName(String firstName) {
		this.fFirstName = firstName;
	}
	
	/**
	 * Returns this person's middle name. 
	 * 
	 * @return This person's middle name.
	 */
	public String getMiddleName () {
		return fMiddleName;
	}
	
	/**
	 * Sets this person's middle name.
	 * 
	 * @param middleName The new middle name.
	 */
	public void setMiddleName (String middleName) {
		this.fMiddleName = middleName;
	}
	
	/**
	 * Returns the gender of this person.
	 * 
	 * @return This person's gender.
	 */
	public Gender getGender () {
		return fGender;
	}
	
	/**
	 * Sets this person's gender.
	 * 
	 * @param gender The gender.
	 */
	public void setGender (Gender gender) {
		this.fGender = gender;
	}
	
	/**
	 * Returns this person's date of birth.
	 * 
	 * @return This person's date of birth.
	 */
	public Date getDOB () {
		return fBirthDate;
	}
	
	/**
	 * Sets this person's date of birth.s
	 * 
	 * @param dob The date of birth.
	 */
	public void setDOB (Date dob) {
		this.fBirthDate = dob;
	}
	
	/**
	 * Returns this persons' user name.
	 * 
	 * @return This person's website user name.
	 */
	public String getUserName () {
		return fUserName;
	}
	
	/**
	 * Sets this person's website user name.
	 * 
	 * @param The new user name.
	 */
	public void setUserName (String userName) {
		this.fUserName = userName;
	}
	
	/**
	 * Returns this person's password.
	 * 
	 * @return This person's website password.
	 */
	public String getPassword () {
		return fPassword;
	}
	
	/**
	 * Sets this person's password.
	 * 
	 * @param password The new password.
	 * @param password
	 */
	public void setPassword (String password) {
		fPassword = password;
	}
	
	/**
	 * Returns this person's contact information.
	 * 
	 * @return This person's contact information.
	 */
	public Contact getContact () {
		return fContact;
	}
	
	/**
	 * Sets this person's contact information.
	 * 
	 * @param contact The new contact information.
	 */
	public void setContact (Contact contact) {
		fContact = contact;
	}
}
