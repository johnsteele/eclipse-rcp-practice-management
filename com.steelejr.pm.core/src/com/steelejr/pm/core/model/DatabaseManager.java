package com.steelejr.pm.core.model;

import java.util.ArrayList;
import java.util.List;

import com.steelejr.pm.core.model.patients.Patient;

public class DatabaseManager {
	
	private static List<Patient> fPatientList = new ArrayList<Patient>();
	
	
	public static List<Patient> getPatients () {
		
		for (int i = 0; i < 100; i++) { 
			Doctor doc = new Doctor("Dr. Foo Bar");
			doc.setFirstName("Foo");
			doc.setLastName("Bar");
			Patient p = new Patient(doc);
			p.setFirstName("John");
			p.setLastName("Steele");
			fPatientList.add(p);
		}
		
		return fPatientList;
	}

}
