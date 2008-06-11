/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for ComponentDependency class.
 * 
 * @author King_Bette
 * @version 1.0
 */
public class ComponentDependencyFailureTest extends TestCase {
	/**
	 * This instance is used in the test.
	 */
	private ComponentDependency componentDependency = new ComponentDependency(
			"component1", "1.0", ComponentLanguage.JAVA,
			DependencyCategory.COMPILE, DependencyType.INTERNAL, "test");

	/**
	 * Aggregates all tests in this class.
	 * 
	 * @return Test suite aggregating all tests.
	 */
	public static Test suite() {
		return new TestSuite(ComponentDependencyFailureTest.class);
	}

	/**
	 * Sets up the environment for the TestCase.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	protected void setUp() throws Exception {
		// do nothing
	}

	/**
	 * Tears down the environment for the TestCase.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	protected void tearDown() throws Exception {
		// do nothing
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * name is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_null_name() throws Exception {
		try {
			new ComponentDependency(null, "1.0", ComponentLanguage.JAVA,
					DependencyCategory.COMPILE, DependencyType.INTERNAL, "test");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * name is empty.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_empty_name() throws Exception {
		try {
			new ComponentDependency("  ", "1.0", ComponentLanguage.JAVA,
					DependencyCategory.COMPILE, DependencyType.INTERNAL, "test");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * version is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_null_version() throws Exception {
		try {
			new ComponentDependency("component1", null, ComponentLanguage.JAVA,
					DependencyCategory.COMPILE, DependencyType.INTERNAL, "test");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * null language.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_null_language()
			throws Exception {
		try {
			new ComponentDependency("component1", "1.0",
					null, DependencyCategory.COMPILE,
					DependencyType.INTERNAL, "test");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * category is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_null_category()
			throws Exception {
		try {
			new ComponentDependency("component1", "1.0",
					ComponentLanguage.JAVA, null, DependencyType.INTERNAL,
					"test");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * type is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_null_type() throws Exception {
		try {
			new ComponentDependency("component1", "1.0",
					ComponentLanguage.JAVA, DependencyCategory.COMPILE, null,
					"test");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * path is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_null_path() throws Exception {
		try {
			new ComponentDependency("component1", "1.0",
					ComponentLanguage.JAVA, DependencyCategory.COMPILE,
					DependencyType.INTERNAL, null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>ComponentDependency(String name, String version, ComponentLanguage language,
	 * DependencyCategory category, DependencyType type, String path)</code>
	 * constructor.
	 * 
	 * <p>
	 * empty path
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testComponentDependency_failure_empty_path() throws Exception {
		try {
			new ComponentDependency("component1", "1.0",
					ComponentLanguage.JAVA, DependencyCategory.COMPILE,
					DependencyType.INTERNAL, "  ");
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>setCategory(DependencyCategory category)</code>
	 * method.
	 * 
	 * <p>
	 * category is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetCategory_failure_null_category() throws Exception {
		try {
			componentDependency.setCategory(null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>setType(DependencyType type)</code> method.
	 * 
	 * <p>
	 * type is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetType_failure_null_type() throws Exception {
		try {
			componentDependency.setType(null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>setPath(String path)</code> method.
	 * 
	 * <p>
	 * path is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetPath_failure_null_path() throws Exception {
		try {
			componentDependency.setPath(null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}
}
