package com.steelejr.pm.core.ui.datasource;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.steelejr.pm.dbaccess.IDatabaseConnectionService;

public class Database {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private IDatabaseConnectionService connectionService;
	
	public synchronized void setConnection (IDatabaseConnectionService connectionService) {
		log.debug("Database Connection Set");
		this.connectionService = connectionService;
		DatabaseMetaData metadata;
		try {
			metadata = connectionService.getConnection().getMetaData();
			log.debug("Database is: " + metadata.getDriverName());
		} catch (SQLException sqlEx) {
			log.error("Unable to get metadata " + sqlEx);
		}
	}
	
	// Called by DS when connection service disappears. 
	public synchronized void releaseConnection (IDatabaseConnectionService connection) {
		log.debug("Releasing connection");
	}
	
	public void startup () {
		log.debug("Startup");
	}
	
	public void shutdown () {
		log.debug("Shutdown");
	}
}
