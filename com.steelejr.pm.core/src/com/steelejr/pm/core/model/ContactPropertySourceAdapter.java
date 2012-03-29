package com.steelejr.pm.core.model;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ContactPropertySourceAdapter implements IPropertySource {
	
	private static final String ADDR1 = "addressLine1";
	private static final String ADDR2 = "addressLine2";
	private static final String CITY  = "city";
	private static final String STATE = "state";
	private static final String ZIP   = "zip";
	private static final String H_PHONE = "homePhone";
	private static final String W_PHONE = "workPhone";
	private static final String C_PHONE = "cellPhone";
	private static final String FAX = "fax";
	private static final String EMAIL = "email";
	
	private Contact fContact;
	
	private static IPropertyDescriptor [] descriptors;
	
	static {
		descriptors = new IPropertyDescriptor[] {
				getDescriptor(ADDR1, "Address Line 1"), 
				getDescriptor(ADDR2, "Address Line 2"),
				getDescriptor(CITY, "City"),
				getDescriptor(STATE, "State"),
				getDescriptor(ZIP, "Zipcode"),
				getDescriptor(H_PHONE, "Home Phone"),
				getDescriptor(W_PHONE, "Work Phone"), 
				getDescriptor(C_PHONE, "Cell Phone"),
				getDescriptor(FAX, "Fax"), 
				getDescriptor(EMAIL, "Email")
		};
	}
	
	public ContactPropertySourceAdapter (Contact contact) {
		this.fContact = contact;
	}
	
	private static TextPropertyDescriptor getDescriptor (String id, String value) {
		return new TextPropertyDescriptor (id, value);
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {		
		if (fContact != null) {
			if (ADDR1.equals(id))        return fContact.getAddress1();
			else if (ADDR2.equals(id))   return fContact.getAddress2();
			else if (CITY.equals(id))    return fContact.getCity();
			else if (STATE.equals(id))   return fContact.getState();
			else if (ZIP.equals(id))     return fContact.getZip();
			else if (H_PHONE.equals(id)) return fContact.getHome_phone();
			else if (W_PHONE.equals(id)) return fContact.getWork_phone();
			else if (C_PHONE.equals(id)) return fContact.getCell_phone();
			else if (FAX.equals(id))     return fContact.getFax();
			else if (EMAIL.equals(id))   return fContact.getEmail();
			else return null;
		}
		else 
			return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		if (ADDR1.equals(id) || 
			ADDR2.equals(id) ||
			CITY.equals(id)  ||
			STATE.equals(id) ||
			ZIP.equals(id)   ||
			H_PHONE.equals(id) ||
			W_PHONE.equals(id) ||
			C_PHONE.equals(id) ||
			FAX.equals(id) ||
			EMAIL.equals(id)) {
			return true;
		}
		
		else 
			return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (fContact != null) {
			if (ADDR1.equals(id)) fContact.setAddress1((String)value);
			else if (ADDR2.equals(id)) fContact.setAddress2((String)value);
			else if (CITY.equals(id)) fContact.setCity((String)value);
			else if (STATE.equals(id)) fContact.setState((String)value);
			else if (ZIP.equals(id)) fContact.setZip((Integer)value);
			else if (H_PHONE.equals(id)) fContact.setHome_phone((String)value);
			else if (W_PHONE.equals(id)) fContact.setWork_phone((String)value);
			else if (C_PHONE.equals(id)) fContact.setCell_phone((String)value);
			else if (EMAIL.equals(id)) fContact.setEmail((String)value);
			else if (FAX.equals(id)) fContact.setFax((String)value);
		}
	}
}
