package com.steelejr.pm.dbaccess.internal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import com.steelejr.pm.dbaccess.IDatabaseConnectionService;

public class DatabaseConnectionService implements IDatabaseConnectionService {

	private DataSourceFactory dataSourceFactory;
	private DataSource dataSource;
	private Connection connection;

	public void setDataSourceFactory (DataSourceFactory factory) {
		dataSourceFactory = factory;
		init ();
	}
	
	private void init () {
		System.out.println("Initaillizing DataSource....");
		Properties props = new Properties();
		props.put(DataSourceFactory.JDBC_URL, "jdbc:derby:testDB;create=true");
		dataSource = null;
		connection = null;
		
		try {
			dataSource = dataSourceFactory.createDataSource(props);
			connection = dataSource.getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			System.out.println("Name: " + metadata.getDriverName());
			System.out.println("Version: " + metadata.getDriverVersion());
			System.out.println("User: " + metadata.getUserName());
			System.out.println("URL: " + metadata.getURL());
		} catch (SQLException sqlEx) {
			System.out.println("Error creating database client: " + sqlEx);
		}
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Override
	public void closeConnection() {
		System.out.println("Closing Database connection...");
		try {
			connection.close();
		} catch (SQLException sqlEx) {
			System.out.println("Error closing connection: " + sqlEx);
		}
	}
}
