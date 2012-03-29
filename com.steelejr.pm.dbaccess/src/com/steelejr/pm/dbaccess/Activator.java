package com.steelejr.pm.dbaccess;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.steelejr.pm.dbaccess.internal.DatabaseConnectionService;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator implements BundleActivator {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public static final String EMBEDDED_DERBY_DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String JDBC_4_VERSION = "4.0";

	private BundleContext context;
	private ServiceReference derbyFactoryRef;
	private ServiceTracker derbyDataSourceFactoryTracker;
	private DataSourceFactory derbyDataSourceFactory;
	private IDatabaseConnectionService connectionService;
	private ServiceRegistration connectionRegistration;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		this.context = context;
		ServiceTrackerCustomizer derbyIzer = getDerbyCustomizer();
		derbyDataSourceFactoryTracker = new ServiceTracker (context, DataSourceFactory.class.getName(), derbyIzer);
		derbyDataSourceFactoryTracker.open();
	}

	public void stop(BundleContext context) throws Exception {
		derbyDataSourceFactoryTracker.close();
		if (connectionRegistration != null)
			connectionRegistration.unregister();
		if (connectionService != null)
			connectionService.closeConnection();
	}
	
	private void bind () {
		
		if (derbyDataSourceFactory == null) {
			derbyDataSourceFactory = (DataSourceFactory) derbyDataSourceFactoryTracker.getService();
			if (derbyDataSourceFactory == null) 
				return; // No DataSourceFactory service available.
		}		
		// We've got the service, now use it.
		connectionService = new DatabaseConnectionService();	
		connectionService.setDataSourceFactory(derbyDataSourceFactory);
		connectionRegistration = context.registerService(IDatabaseConnectionService.class.getName(), connectionService, null); 
	}
	
	private void undbind () {
		if (derbyDataSourceFactory == null) 
			return;		
		// Clean up anything using this service. 		
		derbyDataSourceFactory = null;
	}
	
	private ServiceTrackerCustomizer getDerbyCustomizer () {
		
		return new ServiceTrackerCustomizer () {
			
			public Object addingService(ServiceReference reference) {

				Object service = context.getService(reference);
				
				String driver = (String) reference.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS);
				String version = (String) reference.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION);
				if (driver != null && driver.equalsIgnoreCase(EMBEDDED_DERBY_DRIVER_NAME)
						&& version != null && version.equalsIgnoreCase(JDBC_4_VERSION)) {
					
					synchronized (Activator.this) {
						if (Activator.this.derbyDataSourceFactory == null) {
							Activator.this.derbyDataSourceFactory = (DataSourceFactory) service;
							Activator.this.bind();
						}
					}
				}
				return service;
			}
			
			public void modifiedService(ServiceReference reference,	Object service) {
				// No service property modification to handle.
			}
			
			public void removedService(ServiceReference reference, Object service) {
				synchronized (Activator.this) {
					if (service != Activator.this.derbyDataSourceFactory)
						return;
					Activator.this.undbind();
					Activator.this.bind();
				}
			}
		};
	}
}
