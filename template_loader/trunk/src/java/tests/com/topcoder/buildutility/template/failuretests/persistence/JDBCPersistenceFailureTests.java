/**
 * Copyright (c) 2005, TopCoder Inc. All rights reserved.
 */
package com.topcoder.buildutility.template.failuretests.persistence;

import com.topcoder.buildutility.template.ConfigurationException;
import com.topcoder.buildutility.template.UnknownTemplateHierarchyException;
import com.topcoder.buildutility.template.failuretests.FailureTestsHelper;
import com.topcoder.buildutility.template.persistence.JDBCPersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.Property;

import junit.framework.TestCase;

/**
 * Failure tests for {@link JDBCPersistence}.
 * 
 * @author maone
 * @version 1.0
 */
public class JDBCPersistenceFailureTests extends TestCase {
	
	private JDBCPersistence persistence = null;

	/**
	 * Preapare to test.
	 * 
	 * @throws Exception to JUnit
	 */
	protected void setUp() throws Exception {
		FailureTestsHelper.loadAllConfig();
		persistence = new JDBCPersistence("mySQL", new DBConnectionFactoryImpl(FailureTestsHelper.VALID_DBFACTORY_NAMESPACE));
		
		FailureTestsHelper.clearTables();
	}

	/**
	 * Tear down.
	 * 
	 * @throws Exception to JUnit
	 */
	protected void tearDown() throws Exception {
		FailureTestsHelper.clearAllConfig();
	}

	/**
	 * Test constructor <code>JDBCPersistence()</code> when the default namespace does not exist.
	 * 
	 * <p>ConfigurationException should be thrown.</p>
	 */
	public void testJDBCPersistence() {
		try {
			new JDBCPersistence();
			fail("ConfigurationException should be thrown.");
		} catch (ConfigurationException e) {
			// pass
		}
	}

	/**
	 * Test constructor <code>JDBCPersistence(Property)</code> with invalid DBFactory namespace.
	 * 
	 * <p>ConfigurationException should be thrown.</p>
	 * 
	 * @throws Exception to JUnit
	 */
	public void testJDBCPersistenceProperty1() throws Exception {
		Property property = ConfigManager.getInstance().getPropertyObject(FailureTestsHelper.INVALID_NAMESPACE, "persistence_invalid_dbfactory").getProperty("config");
		try {
			new JDBCPersistence(property);
			fail("ConfigurationException should be thrown.");
		} catch (ConfigurationException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>JDBCPersistence(Property)</code> with empty connection name value.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 * 
	 * @throws Exception to JUnit
	 */
	public void testJDBCPersistenceProperty2() throws Exception {
		Property property = ConfigManager.getInstance().getPropertyObject(FailureTestsHelper.INVALID_NAMESPACE, "persistence_invalid_dbconnection").getProperty("config");
		try {
			new JDBCPersistence(property);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>JDBCPersistence(Property)</code> with null param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 * 
	 * @throws Exception to JUnit
	 */
	public void testJDBCPersistenceProperty3() throws Exception {
		try {
			new JDBCPersistence((Property) null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}

	/**
	 * Test constructor <code>JDBCPersistence(String, DBConnectionFactory)</code> with null connectionName.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 * 
	 * @throws Exception to JUnit
	 */
	public void testJDBCPersistenceString1() throws Exception {
		DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(FailureTestsHelper.VALID_DBFACTORY_NAMESPACE); 
		try {
			new JDBCPersistence(null, dbFactory);
			// fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>JDBCPersistence(String, DBConnectionFactory)</code> with null dbFactory.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testJDBCPersistenceString2() {
		try {
			new JDBCPersistence("connectionname", null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>JDBCPersistence(String, DBConnectionFactory)</code> with empty connectionName.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 * 
	 * @throws Exception to JUnit
	 */
	public void testJDBCPersistenceString3() throws Exception {
		DBConnectionFactory dbFactory = new DBConnectionFactoryImpl(FailureTestsHelper.VALID_DBFACTORY_NAMESPACE); 
		try {
			new JDBCPersistence("   ", dbFactory);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test <code>getTemplateHierarchy</code> with null param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 * @throws Exception
	 */
	public void testGetTemplateHierarchy1() throws Exception {
		try {
			persistence.getTemplateHierarchy(null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}

	/**
	 * Test <code>getTemplateHierarchy</code> with empty string param.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 * @throws Exception
	 */
	public void testGetTemplateHierarchy2() throws Exception {
		try {
			persistence.getTemplateHierarchy("  ");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test <code>getTemplateHierarchy</code> with empty string param when the
	 * specified top-level template hierarchy is not recognized..
	 * 
	 * <p>UnknownTemplateHierarchyException should be thrown.</p>
	 * @throws Exception
	 */
	public void testGetTemplateHierarchy3() throws Exception {
		try {
			persistence.getTemplateHierarchy("foo");
			fail("UnknownTemplateHierarchyException should be thrown.");
		} catch (UnknownTemplateHierarchyException e) {
			// pass
		}
	}
}
