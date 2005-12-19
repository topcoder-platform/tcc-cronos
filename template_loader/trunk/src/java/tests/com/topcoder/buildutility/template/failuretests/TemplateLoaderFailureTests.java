/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.buildutility.template.failuretests;

import com.topcoder.buildutility.template.ConfigurationException;
import com.topcoder.buildutility.template.TemplateHierarchyPersistence;
import com.topcoder.buildutility.template.TemplateLoader;
import com.topcoder.buildutility.template.UnknownTemplateHierarchyException;
import com.topcoder.buildutility.template.persistence.JDBCPersistence;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import junit.framework.TestCase;

/**
 * Failure tests for {@link TemplateLoader}.
 * 
 * @author maone
 * @version 1.0
 */
public class TemplateLoaderFailureTests extends TestCase {

	/**
	 * A <code>TemplateLoader</code> instance for tests.
	 */
	private TemplateLoader loader = null;
	
	/**
	 * A <code>TemplateHierarchyPersistence</code> instance for tests.
	 */
	private TemplateHierarchyPersistence persistence = null;
	
	/**
	 * Prepare to test.
	 * 
	 * @throws Exception to JUnit
	 */
	protected void setUp() throws Exception {
		FailureTestsHelper.loadAllConfig();
		persistence = new JDBCPersistence("mySQL", new DBConnectionFactoryImpl(FailureTestsHelper.VALID_DBFACTORY_NAMESPACE));
		loader = new TemplateLoader(persistence, "failure/");
		
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
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String)</code>
	 * with null persistence param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceString1() {
		try {
			new TemplateLoader(null, "fileServerUri");
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String)</code>
	 * with null fileServerUri param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceString2() {
		try {
			new TemplateLoader(persistence, null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String)</code>
	 * with empty fileServerUri param.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceString3() {
		try {
			new TemplateLoader(persistence, "   ");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}


	/**
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String, long, int)</code>
	 * with null persistence param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceStringlongint1() {
		try {
			new TemplateLoader(null, "fileServerUri", 1, 1);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String, long, int)</code>
	 * with null fileServerUri param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceStringlongint2() {
		try {
			new TemplateLoader(persistence, null, 1, 1);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String, long, int)</code>
	 * with empty fileServerUri param.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceStringlongint3() {
		try {
			new TemplateLoader(persistence, "   ", 1, 1);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String, long, int)</code>
	 * with non-positive timeout value.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceStringlongint4() {
		try {
			new TemplateLoader(persistence, "fileServerUri", 0, 1);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor <code>TemplateLoader(TemplateHierarchyPersistence, String, long, int)</code>
	 * with non-positive maxSize value.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 */
	public void testTemplateLoaderTemplateHierarchyPersistenceStringlongint5() {
		try {
			new TemplateLoader(persistence, "fileServerUri", 1, 0);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test <code>TemplateLoader(String)</code> with null namespace param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 * 
	 * @throws ConfigurationException to JUnit
	 */
	public void testTemplateLoaderString1() throws ConfigurationException {
		try {
			new TemplateLoader(null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test <code>TemplateLoader(String)</code> with empty namespace param.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 * 
	 * @throws ConfigurationException to JUnit
	 */
	public void testTemplateLoaderString2() throws ConfigurationException {
		try {
			new TemplateLoader("  ");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test <code>loadTemplateHierarchy</code> with null param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 * @throws Exception to JUnit
	 */
	public void testLoadTemplateHierarchy1() throws Exception {
		try {
			loader.loadTemplateHierarchy(null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}

	/**
	 * Test <code>loadTemplateHierarchy</code> with empty string param.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 * @throws Exception to JUnit
	 */
	public void testLoadTemplateHierarchy2() throws Exception {
		try {
			loader.loadTemplateHierarchy("   ");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test <code>loadTemplateHierarchy</code> with empty string param when the
	 * specified top-level template hierarchy is not recognized..
	 * 
	 * <p>UnknownTemplateHierarchyException should be thrown.</p>
	 * @throws Exception
	 */
	public void testLoadTemplateHierarchy3() throws Exception {
		try {
			loader.loadTemplateHierarchy("foo");
			fail("UnknownTemplateHierarchyException should be thrown.");
		} catch (UnknownTemplateHierarchyException e) {
			// pass
		}
	}
}
