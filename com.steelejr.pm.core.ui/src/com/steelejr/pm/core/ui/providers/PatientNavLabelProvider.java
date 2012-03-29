package com.steelejr.pm.core.ui.providers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.steelejr.pm.core.model.Person;
import com.steelejr.pm.core.model.patients.Patient;
import com.steelejr.pm.core.ui.Activator;

public class PatientNavLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		if (element instanceof Patient) {
			Patient p = (Patient) element;
			if (p.getGender() == Person.Gender.MALE) 
				return Activator.getImageDescriptor("icons/patients/mail.png").createImage();
			
			else if (p.getGender () == Person.Gender.FEMALE) 
				return Activator.getImageDescriptor("icons/patients/female.png").createImage();
			
			else 
				return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_ELCL_REMOVE_DISABLED);
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		Patient p = (Patient) element;
		return p.getLastName() + ", " + p.getFirstName();
	}
}
