package com.steelejr.pm.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;

public class PatientPerspectiveFactory implements IPerspectiveFactory {
	
	public static final String PERSPECTIVE_ID = "com.steelejr.pm.rcp.patient.perspective";

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(true);
		
		layout.createPlaceholderFolder("topLeft", IPageLayout.LEFT, 0.25f, layout.getEditorArea());
		IPlaceholderFolderLayout leftPlaceholder = layout.createPlaceholderFolder("bottomLeft", IPageLayout.BOTTOM, 0.6f, "topLeft");
		leftPlaceholder.addPlaceholder(IPageLayout.ID_OUTLINE);
		
		IPlaceholderFolderLayout rightPlaceholder = layout.createPlaceholderFolder("bottomRight", IPageLayout.BOTTOM, 0.6f, layout.getEditorArea()); 
		rightPlaceholder.addPlaceholder(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
	}
}
