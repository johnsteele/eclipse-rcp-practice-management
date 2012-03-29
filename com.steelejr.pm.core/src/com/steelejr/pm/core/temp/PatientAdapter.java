package com.steelejr.pm.core.temp;

import org.eclipse.jface.resource.ImageDescriptor;

import com.steelejr.pm.core.model.patients.Patient;
import com.steelejr.pm.core.Activator;

public class PatientAdapter implements IPatientAdapter {
	
	private Patient fPatient;
	
	public PatientAdapter (Patient patient) {
		fPatient = patient;
	}

	public Object[] getChildren(Object o) {
//		Patient [] patients = new Patient [100];
//		for (int i = 0; i < patients.length; i++) { 
//			patients[i] = new Patient();
//			patients[i].setLastName("Child: " + i);
//			patients[i].setContact(new Contact());
//			patients[i].setDOB(new Date());
//			patients[i].setDoctor(new Doctor());
//		}
//		return patients;
		return null;
	}
	
	public ImageDescriptor getImageDescriptor(Object object) {
		return Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/person.gif");
	}

	public String getLabel(Object o) {
		return fPatient.getLastName();
	}

	public Object getParent(Object o) {
		return null;
	}
}
