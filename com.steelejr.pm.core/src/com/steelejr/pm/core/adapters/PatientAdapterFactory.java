package com.steelejr.pm.core.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

import com.steelejr.pm.core.model.patients.Patient;
import com.steelejr.pm.core.model.patients.PatientPropertySourceAdapter;

public class PatientAdapterFactory implements IAdapterFactory {

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		
		if (adapterType == IPropertySource.class && adaptableObject instanceof Patient) {
			return new PatientPropertySourceAdapter((Patient) adaptableObject);
		}
		
		return null;
	}

	@Override
	public Class[] getAdapterList() {
		return new Class[] {IPropertySource.class};
	}

}
