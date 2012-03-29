package com.steelejr.pm.core.model;


public class Doctor extends Employee {
	
	/** Doctor's title */
	private String fTitle;
	
	public Doctor (String title) {
		fTitle = title;
	}
		
	/**
	 * Return this doctor's title.
	 * 
	 * @return This doctor's title.
	 */
	public String getTitle () {
		return fTitle;
	}
	
	/**
	 * Sets this doctor's title.
	 * 
	 * @param The new title.
	 */
	public void setTitle (String title) {
		fTitle = title;
	}
}
