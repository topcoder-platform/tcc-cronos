/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.buildutility.template.failuretests;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper utility for failure tests of TCS Template Loader Component.
 * 
 * @author maone
 */
public class FailureTestsHelper {
	
	public static String INVALID_NAMESPACE = "com.topcoder.buildutility.template.InvalidNamespace";
	
	public static String VALID_DBFACTORY_NAMESPACE = "com.topcoder.buildutility.template.DBFactoryNamespace";

	/**
	 * 
	 */
	private FailureTestsHelper() {
		
	}
	
	public static void loadAllConfig() throws Exception {
		clearAllConfig();
		
		ConfigManager config = ConfigManager.getInstance();
		config.add("failure/failureConfig.xml");
		config.add("failure/failureDBConfig.xml");
		
	}
	
	public static void clearAllConfig() throws Exception {
		ConfigManager config = ConfigManager.getInstance();
		Iterator itor = config.getAllNamespaces();
		while (itor.hasNext()) {
			config.removeNamespace((String) itor.next());
		}
	}
	
	public static void clearTables() throws Exception {
		Connection connection = null;
		
		try {
			DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(VALID_DBFACTORY_NAMESPACE);
			connection = dbFactory.createConnection("mySQL");
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM TEMP_HIER_MAPPING");
			statement.executeUpdate("DELETE FROM TEMP_HIER");
			statement.executeUpdate("DELETE FROM TEMPLATES");
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}
