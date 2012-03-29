package com.steelejr.pm.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PatientPerspectiveFactory implements IPerspectiveFactory {
	
	public static final String PERSPECTIVE_ID = "com.steelejr.pm.rcp.patient.perspective";

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		
		layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, 0.25f, layout.getEditorArea());
		//layout.addView(PatientNavView.ID, IPageLayout.LEFT, 0.5f, layout.getEditorArea());
		//layout.addShowViewShortcut(PatientNavView.ID);
	}
}
