package com.steelejr.pm.core.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.steelejr.pm.core.model.Person.Gender;
import com.steelejr.pm.core.model.patients.Patient;
import com.steelejr.pm.core.ui.Activator;

public class PatientEditorInput implements IEditorInput {
	
	private Patient fPatient;
	public PatientEditorInput (Patient patient) {
		this.fPatient = patient;
	}
	
	public Patient getPatient () {
		return fPatient;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		if (fPatient != null) {
			if (fPatient.getGender() == Gender.MALE)
				return Activator.getImageDescriptor("icons/patients/male.png");
			
			if (fPatient.getGender() == Gender.FEMALE) 
				return Activator.getImageDescriptor("icons/patients/femail.png");
		}
		
		return null;
	}

	@Override
	public String getName() {
		return "patient";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "Patient Tool-tip'";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		
		if (obj == null) return false;

		if (getClass() != obj.getClass()) return false;
		
		PatientEditorInput input = (PatientEditorInput) obj;
		if (this.fPatient.getLastName().equalsIgnoreCase(input.getPatient().getLastName())) {
			System.out.println("Name == Name (" + fPatient.getLastName() + " == " + input.getPatient().getLastName() + ")");
			return true;
		}
		
		return false;
	}
}
