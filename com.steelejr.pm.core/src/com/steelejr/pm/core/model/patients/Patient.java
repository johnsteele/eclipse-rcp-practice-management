package com.steelejr.pm.core.model.patients;

import java.beans.PropertyChangeListener;

import com.steelejr.pm.core.model.Doctor;
import com.steelejr.pm.core.model.Person;



public class Patient extends Person {
	
	/** Patient's doctor. */
	private Doctor fDoctor;
	
	public Patient (Doctor doctor) {
		super();
		fDoctor = doctor;
	}
	
	public void addPropertyChangeListener (String property, PropertyChangeListener listener) {
		
	}
	
	public void setDoctor (Doctor doctor) {
		fDoctor = doctor;
	}
	
	public Doctor getDoctor () {
		return fDoctor;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Patient)) return false;
		if (this == obj) return true;
		
		Patient p = (Patient) obj;
		if (p.getFirstName() != getFirstName()) return false;
		return true;
	}
}
