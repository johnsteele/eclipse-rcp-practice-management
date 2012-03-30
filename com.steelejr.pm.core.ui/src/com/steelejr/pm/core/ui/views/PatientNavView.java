package com.steelejr.pm.core.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.steelejr.pm.core.model.DatabaseManager;
import com.steelejr.pm.core.model.patients.Patient;
import com.steelejr.pm.core.ui.Activator;
import com.steelejr.pm.core.ui.editors.PatientEditor;
import com.steelejr.pm.core.ui.editors.PatientEditorInput;
import com.steelejr.pm.core.ui.providers.PatientContentProvider;
import com.steelejr.pm.core.ui.providers.PatientNavLabelProvider;
import com.steelejr.pm.core.util.PatientFilter;

/**
 * PatientNavigationView displays the list of patients. 
 * It has a text box for quickly searching for a patient, 
 * and buttons for adding and deleting patients. The view
 * also allows filtering the patients.
 */
public class PatientNavView extends ViewPart implements PropertyChangeListener {
	
	public static final String ID = "com.steelejr.pm.core.ui.views.patientNavView";
	
	/**
	 * The name to be displayed in the tab item.
	 */
	private static final String TAB_TITLE = "Patients";	
	private static final String TAB_TOOLTIP = "Search Patients";
	private static final String INITIAL_FILTER_TEXT = "type filter text";
	
	/**
	 * The patient tree viewer.
	 */
	private TreeViewer my_treeViewer;
	
	/**
	 * The filter text for searching a patient.
	 */
	private Text my_filterText;
	
	/**
	 * The list of patients.
	 */
	private WritableList my_patientList;
	
	/**
	 * The patient view filter.
	 */
	private PatientFilter my_viewerFilter;
		
	
    /**
     * Asks this part to take focus within the workbench. Parts must
     * assign focus to one of the controls contained in the part's
     * parent composite.
     */
	@Override
	public void setFocus() {
		my_filterText.setFocus();
	}

	
	/**
	 * Creates the SWT controls for this view. 
	 */
	@Override
	public void createPartControl(Composite parent) {
		
	/* The layout of the parent composite */
		GridLayout layout  = new GridLayout(1, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		Composite comp = new Composite (parent, SWT.NONE);
		comp.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		comp.setLayoutData(data);
		
	/* Button bar*/
		createToolBar(comp);
	
	/* Search text and patient tree viewer. */
		Composite search = new Composite(comp, SWT.BORDER);
		search.setBackground(parent.getParent().getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		search.pack();
		
		GridLayout layout2  = new GridLayout();
		
		search.setLayout(layout2);
		search.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));	
		
		createSearchField(search);
		createTreeViewer(search);
		
		getSite().setSelectionProvider(my_treeViewer);
	}
	
	
	/**
	 * Creates the toolbar above the filtered tree.
	 */
	private void createToolBar (final Composite comp) {
		ToolBar toolBar = new ToolBar(comp, SWT.HORIZONTAL | SWT.FLAT);
		
		/* Add patient */
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/add.gif").createImage());
		item.setToolTipText("New patient");
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// null indicates to open a blank new patient editor.
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(null, PatientEditor.ID);
				} catch (PartInitException e1) {
					System.out.println("Unable to open PatientEditor...");
					e1.printStackTrace();
				}
			}
		});
		
		/* Delete patient */
		item = new ToolItem(toolBar, SWT.PUSH);
		item.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/delete.gif").createImage());
		item.setToolTipText("Delete patient");
		item.setEnabled(true);
		
		item.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if (!my_treeViewer.getSelection().isEmpty()) {
	 				IStructuredSelection selection = (IStructuredSelection) my_treeViewer.getSelection();
					
					Patient p = (Patient) selection.getFirstElement();
					MessageDialog dialog = new MessageDialog(
								comp.getShell(), 
								"Confirm Delete", 								   /* Dialog title. */
								Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/linux.gif").createImage(),   /* Dialog image. */
								"Are you sure you want to delete " + p.toString() + "?", /* The message. */
								MessageDialog.QUESTION, 						   /* Dialog type. */
								new String [] {"OK", "Cancel"}, 				   /* Dialog button labels. */
								0);
					
					int result = dialog.open();
					
					// Delete the patient?
					if (result == 0) {
						//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(editor, save)getWorkbenchPage().closePatientTabs(p);						
						my_patientList.remove(p);
						my_treeViewer.refresh();
					}
				}
				
			}
		});
		
		item = new ToolItem(toolBar, SWT.SEPARATOR);
	}
	
	
	/**
	 * Creates the search text field.
	 */
	private void createSearchField (Composite parent) {
		
		my_filterText = new Text(parent, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		my_filterText.setLayoutData(data);
		
		my_filterText.setText(INITIAL_FILTER_TEXT);
		my_filterText.selectAll();
		// Delete the filter text once clicked.
		my_filterText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (my_filterText.getText().equals(INITIAL_FILTER_TEXT)) {
					my_filterText.setText("");
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (my_filterText.getText().equals("")) {
					my_filterText.setText(INITIAL_FILTER_TEXT);
				}
			}
		});
		
		// Filter the patients while user input.
		my_filterText.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					// If there's 1, it's already selected. See 'keyReleased'.
					if (my_treeViewer.getTree().getItemCount() > 1) {
						Tree tree = my_treeViewer.getTree();
						tree.setSelection(tree.getItem(1));
					}
					my_treeViewer.getTree().setFocus();
				}
				
				if (e.keyCode == SWT.CR) {
					if (!my_treeViewer.getSelection().isEmpty()) {
						IStructuredSelection selection = (IStructuredSelection) my_treeViewer.getSelection();
						Patient p = (Patient) selection.getFirstElement();
						System.out.println("Patient: " + p.getLastName());
						try {
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new PatientEditorInput(p), PatientEditor.ID);
						} catch (PartInitException e1) {
							System.out.println("Unable to open PatientEditor...");
							e1.printStackTrace();
						}	
					}
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				my_viewerFilter.setSearchString(my_filterText.getText());
				my_treeViewer.refresh();
				if (my_treeViewer.getTree().getItemCount() > 0) {
					Tree tree = my_treeViewer.getTree();
					my_treeViewer.getTree().setSelection(tree.getItem(0));
				}
			}				
		});
	}

	
	/**
	 * Creates the tree viewer.
	 */
	private void createTreeViewer (Composite parent) {
	
		my_treeViewer = new TreeViewer(parent, SWT.VIRTUAL | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		my_treeViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		my_treeViewer.setContentProvider(new PatientContentProvider());
		my_treeViewer.setLabelProvider(new PatientNavLabelProvider());
		
		List<Patient> patients = DatabaseManager.getPatients();
		my_patientList = new WritableList(patients, Patient.class);
		my_treeViewer.setInput(my_patientList);
		
		for (Patient p : patients) {
			p.addPropertyChangeListener("my_lastName", this);
			p.addPropertyChangeListener("my_firstName", this);
		}
		my_treeViewer.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				
				if (!my_treeViewer.getSelection().isEmpty()) {
					IStructuredSelection selection = (IStructuredSelection) my_treeViewer.getSelection();
					Patient p = (Patient) selection.getFirstElement();
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new PatientEditorInput(p), PatientEditor.ID);
					} catch (PartInitException e1) {
						System.out.println("Error opening PatientEditor...");
						e1.printStackTrace();
					}
				}
			}
		});
		
		my_treeViewer.getTree().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if (e.keyCode == SWT.CR) {
					if (!my_treeViewer.getSelection().isEmpty()) {
						IStructuredSelection selection = (IStructuredSelection) my_treeViewer.getSelection();
						List<Patient> itemList = selection.toList();
						for (Iterator<Patient> iter = itemList.iterator(); iter.hasNext();) {
							Patient p = (Patient) iter.next();
							try {
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new PatientEditorInput(p), PatientEditor.ID);
							} catch (PartInitException e1) {
								System.out.println("Error opening PatientEditor...");
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		//my_treeViewer.setComparator(new PatientNavViewComparator());
		my_viewerFilter = new PatientFilter();	
		my_treeViewer.addFilter(my_viewerFilter);	
	}
	

	/**
	 * Disposes the workbench recourses, fonts, images, etc.&nbsp;
	 * Also, unregister any listeners from the workbench.
	 */
	@Override
	public void dispose() {
	}
	
	
	/**
	 * Returns the search text for setting focus upon application startup.
	 * 
	 * @return The search text.
	 */
	public Text getFilterText () {
		return my_filterText;
	}
	
	
	/**
	 * Adds the provided listener to the tree viewer.
	 * 
	 * @param the_listener The selection listener. 
	 */
	public void addSelectionListener (SelectionListener the_listener) {
		my_treeViewer.getTree().addSelectionListener(the_listener);
	}


	@Override
	public void propertyChange(PropertyChangeEvent e) {
		my_treeViewer.refresh();
	}
}

