package com.steelejr.pm.core.model;

public class Address {
	
	/** Primary Key ID */
	private long id;
	
	/** Address line 1 */
	private String fAddressLine1;
	
	/** Address line 2 */
	private String fAddressLine2;
	
	/** City */
	private String fCity;
	
	/** Postal Code */
	private String fPostalCode;
	
	public Address () {
		fAddressLine1 = "";
		fAddressLine2 = "";
		fCity         = "";
		fPostalCode   = "";
	}
	
	/**
	 * Sets this Address's line 1.
	 * 
	 * @param line1 The new address line 1.
	 */
	public void setLine1 (String line1) {
		fAddressLine1 = line1;
	}
	
	/**
	 * Return this address's line 1.
	 * 
	 * @return This address's line 1.
	 */
	public String getLine1 () {
		return fAddressLine1;
	}
	
	/**
	 * Sets this Address's line 2.
	 * 
	 * @param line2 The new address line 2.
	 */
	public void setLine2 (String line2) {
		fAddressLine2 = line2;
	}
	
	/**
	 * Sets this address's city.
	 * 
	 * @param city The new city.
	 */
	public void setCity (String city) {
		fCity = city;
	}
	
	/**
	 * Returns this address's city.
	 * 
	 * @return This address's city.
	 */
	public String getCity () {
		return fCity;
	}
	
	/**
	 * Sets this address's postal code.
	 * 
	 * @param code The new postal code.
	 */
	public void setPostalCode (String code) {
		fPostalCode = code;
	}
	
	/**
	 * Return this address's postal code.
	 * 
	 * @return This address's postal code.
	 */
	public String getPostalCode () {
		return fPostalCode;
	}
}
