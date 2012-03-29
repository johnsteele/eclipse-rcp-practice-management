package com.steelejr.pm.core.temp;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IPatientAdapter {
	public Object[] getChildren (Object parentElement);
	public ImageDescriptor getImageDescriptor(Object object);
	public String getLabel(Object o);
	public Object getParent(Object o);
}
