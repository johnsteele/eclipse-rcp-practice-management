package com.steelejr.pm.core.util;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.steelejr.pm.core.model.patients.Patient;

public class PatientFilter extends ViewerFilter {
	
	private String searchString;
	
	public void setSearchString (String search) {
		// A substring
		this.searchString = ".*" + search + ".*";
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		if (searchString == null || searchString.length() == 0) return true;
		
		Patient p = (Patient) element; 
		if (p.getLastName().matches(searchString))
			return true;
		
		if (p.getFirstName().matches(searchString))
			return true;
		
		
		return false;
	}
}
