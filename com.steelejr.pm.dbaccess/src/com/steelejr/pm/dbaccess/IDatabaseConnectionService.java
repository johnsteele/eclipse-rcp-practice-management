package com.steelejr.pm.dbaccess;

import java.sql.Connection;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

public interface IDatabaseConnectionService {
	public void setDataSourceFactory (DataSourceFactory factory);
	public Connection getConnection ();
	public DataSource getDataSource ();
	public void closeConnection ();
}
