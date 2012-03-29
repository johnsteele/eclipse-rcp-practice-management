package com.steelejr.pm.core.model.patients;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.steelejr.pm.core.model.PersonPropertySourceAdapter;

public class PatientPropertySourceAdapter implements IPropertySource {
	
	private static final String DOC_FIRST = "docFirstName";
	private static final String DOC_LAST  = "docLastName";
	private static final String PERSON    = "person";
	
	private final Patient fPatient;
	
	public PatientPropertySourceAdapter (Patient patient) {
		this.fPatient = patient;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor [] desc =  new IPropertyDescriptor [] {
				new TextPropertyDescriptor(DOC_FIRST, "Doctor First Name"),
				new TextPropertyDescriptor(DOC_LAST, "Doctor Last Name"),
				new PropertyDescriptor(PERSON, "Patient")
		};
		
		((PropertyDescriptor)desc[0]).setCategory("Doctor");
		((PropertyDescriptor)desc[1]).setCategory("Doctor");
		
		return desc;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (DOC_FIRST.equals(id))     return fPatient.getDoctor().getFirstName();
		else if (DOC_LAST.equals(id)) return fPatient.getDoctor().getLastName();
		else if (PERSON.equals(id))   return new PersonPropertySourceAdapter(fPatient);
		else return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if ("doctorFirstName".equals(id)) fPatient.getDoctor().setFirstName((String)value);
		else if ("doctorLastName".equals(id)) fPatient.getDoctor().setLastName((String)value);
	}
}
