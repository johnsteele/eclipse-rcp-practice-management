package com.steelejr.pm.core.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

import com.steelejr.pm.core.model.Person;
import com.steelejr.pm.core.model.patients.Patient;
import com.steelejr.pm.core.ui.Activator;


public class PatientEditor extends EditorPart {
	
	/** 
	 * The editor ID.
	 */
	public static final String ID = "com.steelejr.pm.core.ui.editors.patientEditor";
	
	/**
	 * The editor form.
	 */
	private ScrolledForm fForm;
	
	/**
	 * The form toolkit.
	 */
	private FormToolkit fToolkit = new FormToolkit(Display.getCurrent());
	
	/**
	 * This form's patient model object.
	 */
	private Patient fPatient;
	private PatientEditorInput fPatientInput;
	
	/**
	 * The save action.
	 */
	private Action fSaveAction;
	

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	// This will be called before createPartControl(...)
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		
		PatientEditorInput pInput = (PatientEditorInput) input;	
		fPatientInput = pInput;
		
		fPatient = fPatientInput.getPatient();
		
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		Composite comp = new Composite (parent, SWT.NONE);
		comp.setLayout(layout);
		comp.setLayoutData (new GridData (SWT.FILL, SWT.FILL, true, true));
		
		fForm = fToolkit.createScrolledForm(comp);	
		fForm.setLayout(new GridLayout(2, true));
		fForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		layout = new GridLayout (2, true);
		fForm.getBody().setLayout(layout);
		
		/* Form header */
		setFormHeader ();
		createFormToolbar ();
		paintHeaderBackground ();
		
		/* Patient Details */
		createPersonalSection ();
		createWebsiteSection ();
		
		/* Eye care information */
		createDoctorSection ();
		
		/* Contact details */
		createContactSection ();
		
		Dialog.applyDialogFont(fForm.getBody());
		setFormHeader();
		fForm.reflow(true);
	}

	
	/**
	 * Sets the header text on the form.
	 */
	private void setFormHeader () {
		int lastLength = fPatient.getLastName().length();
		int firstLength = fPatient.getFirstName().length();
		String lastName = fPatient.getLastName();
		String firstName = fPatient.getFirstName();
		
		if (lastLength > 0 && firstLength > 0) {
			fForm.setText(lastName + ", " + firstName);
			setPartName(lastName + ", " + firstName);
		}
		
		else if (lastLength > 0 && firstLength == 0) {
			fForm.setText(lastName);
			setPartName(lastName);
		}
		
		else if (firstLength > 0 && lastLength == 0) {
			fForm.setText(firstName);
			setPartName(firstName);
		}
		
		else {
			fForm.setText("New Patient");
			setPartName("New Patient");
		}
	}
	
	
	/**
	 * Creates the form toolbar.
	 */
	private void createFormToolbar () {
		
		// You can create the action and add it to the manager, which in tern will
		// create the tool item.
		fSaveAction = new Action() {
			@Override
			public void run() {
				System.out.println("Performing save...");
			}
		};
		fSaveAction.setEnabled(true);
		fSaveAction.setImageDescriptor(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/Save.gif"));
		fForm.getToolBarManager().add(fSaveAction);
		
		
		// Or you can create the contribution item and explicitly give it the action.
//		ActionContributionItem item = new ActionContributionItem(save);
//		fForm.getToolBarManager().add(item);
		
		// This contribution is specific to toolbars.
//		ControlContribution save2 = new ControlContribution("save") {
//			@Override
//			protected Control createControl(Composite parent) {
//				Button b = new Button (parent, SWT.PUSH);
//				b.setImage(WorkbenchImages.getImageRegistry().get("save"));
//				b.setToolTipText("Save changes");
//				//b.setEnabled(false);
//				return b;
//			}
//		};
//		fForm.getToolBarManager().add(save2);
		fForm.getToolBarManager().update(true);
	}
	
	@Override
	public void setFocus() {
	}

	
	/**
	 * Paints the gradient on the header background.
	 */
	private void paintHeaderBackground () {
		fToolkit.decorateFormHeading(fForm.getForm());
	}
	
	
	/**
	 * Creates the personal information section.
	 */
	private void createPersonalSection () {
		Section section = fToolkit.createSection(fForm.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		section.setActiveToggleColor(fToolkit.getHyperlinkGroup().getActiveBackground());
		section.setToggleColor(fToolkit.getColors().getColor(IFormColors.SEPARATOR));
		section.setText("Personal Details");
		section.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite client = fToolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout(2, false));
		client.setLayoutData(new GridData (GridData.FILL_BOTH));
		client.pack();
		section.setClient(client);
		fToolkit.paintBordersFor(client);
		
		/* First, Last, Gender, DOB */		
		fToolkit.createLabel(client, "First Name: ");
		final Text firstText = fToolkit.createText(client, fPatient.getFirstName());
		firstText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		firstText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.setFirstName(firstText.getText());
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		});
		
		fToolkit.createLabel(client, "Last Name: ");
		final Text lastText = fToolkit.createText(client, fPatient.getLastName());
		lastText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		lastText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.setLastName(lastText.getText());
			}
		});
		
		fToolkit.createLabel(client, "Gender: ");
		final Combo genderCombo = new Combo(client, SWT.READ_ONLY | SWT.POP_UP);
		genderCombo.add("Male");
		genderCombo.add("Female");
		genderCombo.select(fPatient.getGender() == Person.Gender.MALE ? 0 : 1);
		genderCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.setGender((genderCombo.getSelectionIndex() == 0) ? Person.Gender.MALE : Person.Gender.FEMALE);
			}
		});
		
		fToolkit.createLabel(client, "Date of Birth: ");
		final Text dobText = fToolkit.createText(client, fPatient.getDOB().toString());
		dobText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		dobText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO: fPatient.setDOB(dobText.getText());
			}
		});
		
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				fForm.reflow(true);
			}
		});
		section.pack();
	}
	
	
	private void createWebsiteSection () {
		Section section = fToolkit.createSection(fForm.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
		section.setActiveToggleColor(fToolkit.getHyperlinkGroup().getActiveBackground());
		section.setToggleColor(fToolkit.getColors().getColor(IFormColors.SEPARATOR));
		
		section.setLayoutData(new GridData (GridData.FILL_BOTH));
		
		Composite client = fToolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout (2, false));
		client.setLayoutData(new GridData (GridData.FILL_BOTH));
		client.pack();
		fToolkit.paintBordersFor(client);
		section.setClient(client);
		
		/* Email, Username, Password */
		fToolkit.createLabel(client, "Email: ");
		final Text emailText = fToolkit.createText(client, fPatient.getContact().getEmail());
		emailText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		emailText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setEmail(emailText.getText());
			}
		});
		
		fToolkit.createLabel (client, "User Name: ");
		final Text userText = fToolkit.createText (client, fPatient.getUserName());
		userText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		userText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.setUserName(userText.getText());
			}
		});
		
		fToolkit.createLabel (client, "Password: ");
		final Text passText = fToolkit.createText(client, fPatient.getPassword());
		passText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		passText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.setPassword(passText.getText());
			}
		});
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				fForm.reflow(true);
			}
		});
		section.pack();
	}
	
	private void createDoctorSection () {
		Section section = fToolkit.createSection(fForm.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		GridData data = new GridData (GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		section.setLayoutData(data);
		section.setText("Eye Care Information");
		section.setExpanded(true);
		
		Composite client = fToolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout (2, true));
		client.setLayoutData(new GridData (GridData.FILL_BOTH));
		fToolkit.paintBordersFor(client);
		section.setClient(client);
		
		/* Practice info */
		Composite practiceComp = fToolkit.createComposite(client, SWT.WRAP);
		practiceComp.setLayout(new GridLayout (2, false));
		practiceComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		fToolkit.paintBordersFor(practiceComp);
		
		fToolkit.createLabel(practiceComp, "Practice: ");
		fToolkit.createText(practiceComp, "UW Optical", SWT.READ_ONLY).
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
//		fToolkit.createLabel(practiceComp, "Location: ");
//		final Combo locationCombo = new Combo(practiceComp, SWT.READ_ONLY | SWT.POP_UP);
//		locationCombo.add("1342 North 85th Seattle WA 98105");
//		locationCombo.add("5519 Sounth Brooklyn Seattle WA 90815");
//		locationCombo.add("44 West Madison Bellevue WA 99025");
//		locationCombo.add("25 East Olympic Olympia WA 98025");
//		locationCombo.add("--");
//		locationCombo.select(fPatient.getEyeCareInfo().getLocationIndex());
//		locationCombo.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fPatient.getEyeCareInfo().setLocationIndex(locationCombo.getSelectionIndex());
//			}
//		});
		
		
		fToolkit.createLabel(practiceComp, "Doctor: ");
		final Text docText = fToolkit.createText(practiceComp, fPatient.getDoctor().getFirstName());
		docText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		docText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getDoctor().setFirstName(docText.getText());
			}
		});
		
		/* Exam section */
		Composite examComp = fToolkit.createComposite(client, SWT.WRAP);
		examComp.setLayout(new GridLayout (2, false));
		examComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		fToolkit.paintBordersFor(examComp);
		
//		fToolkit.createLabel(examComp, "Patient Discount (%): ");
//		final Text discountText = fToolkit.createText(examComp, String.valueOf(fPatient.getEyeCareInfo().getDiscount()));
//		discountText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
//		discountText.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				String discount = discountText.getText();
//				if (discount.length() > 0) { 
//					fPatient.getEyeCareInfo().setDiscount(Integer.valueOf(discount));
//				}
//			}
//		});
		
//		fToolkit.createLabel(examComp, "Last Exam Date: ");
//		final Text lastExamText = fToolkit.createText(examComp, fPatient.getEyeCareInfo().getLastExam());
//		lastExamText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
//		lastExamText.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				fPatient.getEyeCareInfo().setLastExam(lastExamText.getText());
//			}
//		});
		
//		fToolkit.createLabel(examComp, "Next Exam Date: ");
//		final Text nextExamText = fToolkit.createText(examComp, fPatient.getEyeCareInfo().getNextExam());
//		nextExamText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
//		nextExamText.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				fPatient.getEyeCareInfo().setNextExam(nextExamText.getText());
//			}
//		});
		
//		fToolkit.createLabel(examComp, "Email notification?");
//		Composite emailGroup = fToolkit.createComposite(examComp, SWT.WRAP);
//		emailGroup.setLayout(new GridLayout(2, false));
//		emailGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
//		
//		boolean notify = fPatient.getEyeCareInfo().isEmailNotification();
//		
//		final Button yesButton = fToolkit.createButton(emailGroup, "Yes", SWT.CHECK);
//		yesButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fPatient.getEyeCareInfo().setEmailNotification(true);
//			}
//		});
		
//		final Button noButton  = fToolkit.createButton(emailGroup, "No", SWT.CHECK);
//		noButton.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				fPatient.getEyeCareInfo().setEmailNotification(false);
//			}
//		});
//		if (notify) {
//			yesButton.setSelection(true);
//			noButton.setSelection(false);
//		}
//		else {
//			noButton.setSelection(true);
//			yesButton.setSelection(false);
//		}
//		
			
// 	 	emailGroup.pack();
		
		Label notesLabel = fToolkit.createLabel(client, "Practioner Notes");
		data = new GridData (GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		data.verticalAlignment = SWT.BOTTOM;
		notesLabel.setLayoutData(data);
		notesLabel.pack();
		
		
//		final Text notesText = fToolkit.createText(client, fPatient.getEyeCareInfo().getNotes() + "\n\n\n", SWT.V_SCROLL | SWT.MULTI);
//		data = new GridData (GridData.FILL_BOTH);
//		data.horizontalSpan = 2;
//		notesText.setLayoutData(data);
//		notesText.setSize(200, 400);
//		notesText.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//				fPatient.getEyeCareInfo().setNotes(notesText.getText());
//			}
//		});
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				fForm.reflow(true);
			}
		});
		
		//section.pack();
	}
	
	
	/**
	 * Creates the contact section. 
	 */
	private void createContactSection () {
		Section section = fToolkit.createSection(fForm.getBody(), Section.TWISTIE | Section.DESCRIPTION | Section.TITLE_BAR);
		GridData data = new GridData (GridData.FILL_BOTH);
		data.horizontalSpan = 2;
		section.setLayoutData(data);
		section.setExpanded(true);
		section.setText("Patient Contact");
		
		/* Client composite */
		Composite client = fToolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout (2, true));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);
		fToolkit.paintBordersFor(client);
		
		/* Address info */
		Composite addressComp = fToolkit.createComposite(client, SWT.WRAP);
		addressComp.setLayout(new GridLayout (2, false));
		addressComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		fToolkit.paintBordersFor(addressComp);
		
		// Address1
		fToolkit.createLabel(addressComp, "Address1: ");
		final Text addr1Text = fToolkit.createText(addressComp, fPatient.getContact().getAddress1());
		addr1Text.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		addr1Text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setAddress1(addr1Text.getText());
			}
		});
		
		// Address2
		fToolkit.createLabel(addressComp, "Address2: ");
		final Text addr2Text = fToolkit.createText(addressComp, fPatient.getContact().getAddress2());
		addr2Text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		addr2Text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setAddress2(addr2Text.getText());
			}
		}); 
	
		// City
		fToolkit.createLabel(addressComp, "City: ");
		final Text cityText = fToolkit.createText(addressComp, fPatient.getContact().getCity());
		cityText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		cityText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setCity(cityText.getText());
			}
		});
		
		// State
		fToolkit.createLabel(addressComp, "State: ");
		final Text stateText = fToolkit.createText(addressComp, fPatient.getContact().getState());
		stateText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		stateText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setState(stateText.getText());
			}
		});
		
		// Zip
		fToolkit.createLabel(addressComp, "Zipcode: ");
		final Text zipText = fToolkit.createText(addressComp, String.valueOf(fPatient.getContact().getZip()));
		zipText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));	
		zipText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setZip(Integer.valueOf(zipText.getText()));
			}
		});
		
		/* Contact info */
		Composite contactComp = fToolkit.createComposite(client, SWT.WRAP);
		contactComp.setLayout(new GridLayout (2, false));
		contactComp.setLayoutData(new GridData (GridData.FILL_BOTH));
		fToolkit.paintBordersFor(contactComp);
		
		// Phone
		fToolkit.createLabel(contactComp, "Phone: ");
		final Text phoneText = fToolkit.createText(contactComp, fPatient.getContact().getHome_phone());
		phoneText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		phoneText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setHome_phone(phoneText.getText());
			}
		});
		
		// Mobile Phone
		fToolkit.createLabel(contactComp, "Mobile Phone: ");
		final Text mobileText = fToolkit.createText(contactComp, fPatient.getContact().getCell_phone());
		mobileText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		mobileText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setCell_phone(mobileText.getText());
			}
		});
		
		// Business Phone
		fToolkit.createLabel(contactComp, "Business: ");
		final Text businessText = fToolkit.createText(contactComp, fPatient.getContact().getWork_phone());
		businessText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		businessText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setWork_phone(businessText.getText());
			}
		});
		
		// Fax
		fToolkit.createLabel(contactComp, "Fax: ");
		final Text faxText = fToolkit.createText(contactComp, fPatient.getContact().getFax());
		faxText.setLayoutData(new GridData (SWT.FILL, SWT.FILL, true, false));
		faxText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fPatient.getContact().setFax(faxText.getText());
			}
		});
		
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				fForm.reflow(true);
			}
		});
		section.pack();
	}
}
