package com.steelejr.pm.dbaccess.internal;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.steelejr.pm.dbaccess.IDatabaseConnectionService;

public class DatabaseConnectionService implements IDatabaseConnectionService {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

	private DataSourceFactory dataSourceFactory;
	private DataSource dataSource;
	private Connection connection;

	public void setDataSourceFactory (DataSourceFactory factory) {
		dataSourceFactory = factory;
		init ();
	}
	
	private void init () {
		log.debug("Initializing DataSource...");
		Properties props = new Properties();
		props.put(DataSourceFactory.JDBC_URL, "jdbc:derby:testDB;create=true");
		dataSource = null;
		connection = null;
		
		try {
			dataSource = dataSourceFactory.createDataSource(props);
			connection = dataSource.getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			log.debug("Name: " + metadata.getDriverName());
			log.debug("Version: " + metadata.getDriverVersion());
			log.debug("User: " + metadata.getUserName());
			log.debug("URL: " + metadata.getURL());
		} catch (SQLException sqlEx) {
			log.error("Error creating database client: " + sqlEx);
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
		log.debug("Closing Database connection...");
		try {
			connection.close();
		} catch (SQLException sqlEx) {
			log.error("Error closing connection: " + sqlEx);
		}
	}
}
