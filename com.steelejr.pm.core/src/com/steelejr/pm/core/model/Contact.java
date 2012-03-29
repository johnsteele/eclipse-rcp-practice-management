package com.steelejr.pm.core.model;

/**
 * Retains contact information.
 * 
 * @author John Steele
 */
public class Contact {

	private String address1;
	private String address2;
	
	private String city;
	private String state;
	private int zip;
	
	private String home_phone;
	private String cell_phone;
	private String work_phone;
	private String fax;
	
	private String email;
	
	public Contact () {
		address1 = "";
		address2 = "";
		city = "";
		state = "";
		zip = 0;
		home_phone = "";
		cell_phone = "";
		work_phone = "";
		fax = "";
		email = "";
	}
	
	public Contact (String addr1, String addr2, String addr3, String city, String state, int zip, 
							String h_phone, String c_phone, String w_phone, String fax, String email) {
		this.address1 = addr1;
		this.address2 = addr2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.home_phone = h_phone;
		this.cell_phone = c_phone;
		this.work_phone = w_phone;
		this.fax = fax;
		this.email = email;
	}
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getHome_phone() {
		return home_phone;
	}
	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}
	public String getCell_phone() {
		return cell_phone;
	}
	public void setCell_phone(String cell_phone) {
		this.cell_phone = cell_phone;
	}
	public String getWork_phone() {
		return work_phone;
	}
	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}	
	public String getEmail () {
		return email;
	}
	public void setEmail (String email) {
		this.email = email;
	}
}

