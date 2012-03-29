package com.steelejr.pm.core.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class PersonPropertySourceAdapter implements IPropertySource {
	
	private static final String FIRST  = "firstName";
	private static final String MIDDLE = "middleName";
	private static final String LAST   = "lastName";
	private static final String CONTACT = "contact";
	
	private final Person fPerson;
	
	public PersonPropertySourceAdapter (Person person) {
		this.fPerson = person;
	}

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		
		PropertyDescriptor descriptor = new PropertyDescriptor(CONTACT, "Contact");
		descriptor.setCategory("Contact");
		
		IPropertyDescriptor [] descriptors =  new IPropertyDescriptor [] {
			new TextPropertyDescriptor(FIRST,  "First Name"),
			new TextPropertyDescriptor(MIDDLE, "Middle Name"),
			new TextPropertyDescriptor(LAST,   "Last Name"), 
			descriptor
		};
		
		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (FIRST.equals(id)) return fPerson.getFirstName();
		else if (MIDDLE.equals(id)) return fPerson.getMiddleName();
		else if (LAST.equals(id)) return fPerson.getLastName();
		else if (CONTACT.equals(id)) return new ContactPropertySourceAdapter(fPerson.getContact());
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
		if (FIRST.equals(id)) fPerson.setFirstName((String)value);
		else if (MIDDLE.equals(id)) fPerson.setMiddleName((String)value);
		else if (LAST.equals(id)) fPerson.setLastName((String)value);
	}
}
