package com.steelejr.pm.core.ui.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.steelejr.pm.core.model.patients.Patient;

public class PatientContentProvider implements ITreeContentProvider {
	
	List<Patient> fModel = new ArrayList<Patient>();

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		fModel = (List<Patient>) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return fModel.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public void dispose() {
	}
}
