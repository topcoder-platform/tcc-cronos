/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.buildutility.template.failuretests;

import com.topcoder.buildutility.template.Template;

import junit.framework.TestCase;

/**
 * Failure tests for {@link Template}.
 * 
 * @author maone
 * @version 1.0
 */
public class TemplateFailureTests extends TestCase {
	
	/**
	 * A <code>Template</code> instance for tests.
	 */
	private Template template = null;

	/**
	 * Prepare to test.
	 */
	protected void setUp() {
		template = new Template(0, "name", "description", "fileName", "uri");
	}

	/**
	 * Test constructor with null <code>name</code> param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplate1() {
		try {
			new Template(0, null, "desc", "fileName", "uri");
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor with null <code>fileName</code> param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplate2() {
		try {
			new Template(1, "name", "desc", null, "uri");
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor with null <code>uri</code> param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplate3() {
		try {
			new Template(-1, "name", "desc", "fileName", null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor with empty <code>name</code> param.
	 *
	 * <p>IllegalArgumentException should be thrown.
	 */
	public void testTemplate4() {
		try {
			new Template(0, "  ", "desc", "fileName", "uri");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor with empty <code>description</code> param.
	 *
	 * <p>IllegalArgumentException should be thrown.
	 */
	public void testTemplate5() {
		try {
			new Template(0, "name", "", "fileName", "uri");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor with empty <code>fileName</code> param.
	 *
	 * <p>IllegalArgumentException should be thrown.
	 */
	public void testTemplate6() {
		try {
			new Template(0, "name", "desc", " ", "uri");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor with empty <code>uri</code> param.
	 *
	 * <p>IllegalArgumentException should be thrown.
	 */
	public void testTemplate7() {
		try {
			new Template(0, "name", "desc", "fileName", "");
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test <code>getData</code> when the fileServerUri is not set.
	 *
	 * <p>IllegalStateException should be thrown.</p>
	 * 
	 * @throws Exception to JUnit
	 */
	public void testGetData1() throws Exception {
		try
		{
			template.getData();
			fail("IllegalStateException should be thrown.");
		} catch (IllegalStateException e) {
			// pass
		}
	}
}
