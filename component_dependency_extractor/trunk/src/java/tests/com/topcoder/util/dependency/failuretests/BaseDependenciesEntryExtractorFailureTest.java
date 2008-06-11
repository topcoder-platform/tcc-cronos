/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import java.util.HashSet;
import java.util.Set;

import com.topcoder.util.dependency.BaseDependenciesEntryExtractor;
import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.DependencyCategory;
import com.topcoder.util.dependency.DependencyType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for BaseDependenciesEntryExtractor class.
 * 
 * @author King_Bette
 * @version 1.0
 */
public class BaseDependenciesEntryExtractorFailureTest extends TestCase {
	/**
	 * This instance is used in the test.
	 */
	private BaseDependenciesEntryExtractor extractor = new BaseDependenciesEntryExtractor() {
		public DependenciesEntry extract(String filePath) {
			return null;
		}

		public boolean isSupportedFileType(String filePath) {
			return false;
		}
	};

	/**
	 * Aggregates all tests in this class.
	 * 
	 * @return Test suite aggregating all tests.
	 */
	public static Test suite() {
		return new TestSuite(BaseDependenciesEntryExtractorFailureTest.class);
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
	 * Failure test of <code>setExtractedTypes(Set<DependencyType> types)</code>
	 * method.
	 * 
	 * <p>
	 * types is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetExtractedTypes_failure_null_types() throws Exception {
		try {
			extractor.setExtractedTypes(null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>setExtractedTypes(Set<DependencyType> types)</code>
	 * method.
	 * 
	 * <p>
	 * types is empty.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetExtractedTypes_failure_empty_types() throws Exception {
		try {
			extractor.setExtractedTypes(new HashSet<DependencyType>());
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of <code>setExtractedTypes(Set<DependencyType> types)</code>
	 * method.
	 * 
	 * <p>
	 * types contains null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetExtractedTypes_failure_types_contains_null()
			throws Exception {
		Set<DependencyType> types = new HashSet<DependencyType>();
		types.add(DependencyType.EXTERNAL);
		types.add(null);
		try {
			extractor.setExtractedTypes(types);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>setExtractedCategories(Set<DependencyCategory> categories)</code>
	 * method.
	 * 
	 * <p>
	 * categories is null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetExtractedCategories_failure_null_categories() throws Exception {
		try {
			extractor.setExtractedCategories(null);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>setExtractedCategories(Set<DependencyCategory> categories)</code>
	 * method.
	 * 
	 * <p>
	 * categories is empty.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetExtractedCategories_failure_empty_categories() throws Exception {
		try {
			extractor.setExtractedCategories(new HashSet<DependencyCategory>());
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}

	/**
	 * Failure test of
	 * <code>setExtractedCategories(Set<DependencyCategory> categories)</code>
	 * method.
	 * 
	 * <p>
	 * categories contains null.
	 * </p>
	 * 
	 * <p>
	 * Expect IllegalArgumentException.
	 * </p>
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetExtractedCategories_failure_categories_contains_null() throws Exception {
		Set<DependencyCategory> categories = new HashSet<DependencyCategory>();
		categories.add(DependencyCategory.COMPILE);
		categories.add(null);
		try {
			extractor.setExtractedCategories(categories);
			fail("Expect IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			// expect
		}
	}
}
