package com.steelejr.pm.core.ui.datasource;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import com.steelejr.pm.dbaccess.IDatabaseConnectionService;

public class Database {
	
	private IDatabaseConnectionService connectionService;
	
	public synchronized void setConnection (IDatabaseConnectionService connectionService) {
		System.out.println("Database Connection Set");
		this.connectionService = connectionService;
		DatabaseMetaData metadata;
		try {
			metadata = connectionService.getConnection().getMetaData();
			System.out.println("Database is: " + metadata.getDriverName());
		} catch (SQLException sqlEx) {
			System.out.println("Unable to get metadata " + sqlEx);
		}
	}
	
	// Called by DS when connection service disappears. 
	public synchronized void releaseConnection (IDatabaseConnectionService connection) {
		System.out.println("Releasing connection");
	}
	
	public void startup () {
		System.out.println("Startup");
	}
	
	public void shutdown () {
		System.out.println("Shutdown");
	}
}
