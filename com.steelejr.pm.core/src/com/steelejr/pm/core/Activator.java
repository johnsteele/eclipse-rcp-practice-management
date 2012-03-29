package com.steelejr.pm.core;

import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.steelejr.pm.core.adapters.PatientAdapterFactory;
import com.steelejr.pm.core.model.patients.Patient;

public class Activator extends AbstractUIPlugin {
	
	public static final String PLUGIN_ID = "com.steelejr.pm.core";

	private static Activator plugin;
	
	public Activator () {		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		plugin = this;		
		
		// Programmatically add adapter factory.
		IAdapterManager manager = Platform.getAdapterManager();
		manager.registerAdapters(new PatientAdapterFactory(), Patient.class);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		plugin = null;
		super.stop(bundleContext);
	}	
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault () {
		return plugin;
	}
	
	
	public static ImageDescriptor getImageDescriptor (String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
