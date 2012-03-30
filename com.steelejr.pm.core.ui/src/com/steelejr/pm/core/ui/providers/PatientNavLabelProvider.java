package com.steelejr.pm.core.ui.providers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.steelejr.pm.core.model.patients.Patient;
import com.steelejr.pm.core.ui.Activator;

public class PatientNavLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof Patient) {
			return Activator.getImageDescriptor("icons/objView/project_person.gif").createImage();
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		Patient p = (Patient) element;
		return p.getLastName() + ", " + p.getFirstName();
	}
	
}
