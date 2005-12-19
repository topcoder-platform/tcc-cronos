/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved.
 */
package com.topcoder.buildutility.template.failuretests;

import com.topcoder.buildutility.template.DuplicateObjectException;
import com.topcoder.buildutility.template.Template;
import com.topcoder.buildutility.template.TemplateHierarchy;

import junit.framework.TestCase;

/**
 * Failure tests for {@link TemplateHierarchy}.
 * 
 * @author maone
 * @version 1.0
 */
public class TemplateHierarchyFailureTests extends TestCase {

	/**
	 * A <code>TemplateHierarchy</code> instance for tests.
	 */
	private TemplateHierarchy hierarchy = null;
	
	/**
	 * A <code>TemplateHierarchy</code> instance for tests.
	 */
	private TemplateHierarchy sub_hierarchy = null;
	
	/**
	 * A <code>Template</code> instance for tests.
	 */
	private Template template = null;
	
	/**
	 * Prepare to test.
	 */
	protected void setUp() throws Exception {
		// create the help objects.
		template = new Template(100, "name", "desc", "fileName", "uri");
		hierarchy = new TemplateHierarchy(0, "name", -1);
		sub_hierarchy = new TemplateHierarchy(1, "name", 0);
		
		// make the relationships
		hierarchy.addTemplate(template);
		hierarchy.addNestedHierarchy(sub_hierarchy);
	}

	/**
	 * Test constructor with null <code>name</code> param.
	 * 
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testTemplateHierarchy1() {
		try {
			new TemplateHierarchy(0, null, 1);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}
	
	/**
	 * Test constructor with empty <code>name</code> param.
	 * 
	 * <p>IllegalArgumentException should be thrown.</p>
	 */
	public void testTemplateHierarchy2() {
		try {
			new TemplateHierarchy(0, " ", 1);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

	/**
	 * Test <code>addTemplate</code> with null param.
	 *
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testAddTemplate1() {
		try {
			hierarchy.addTemplate(null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e){
			// pass
		}
	}
	
	/**
	 * Test <code>addTemplate</code> with a template which has been added.
	 *
	 * <p>DuplicateObjectException should be thrown.</p>
	 */
	public void testAddTemplate2() {
		try {
			hierarchy.addTemplate(template);
			fail("DuplicateObjectException should be thrown.");
		} catch (DuplicateObjectException e){
			// pass
		}
	}

	/**
	 * Test <code>addNestedHierarchy</code> with null param.
	 *
	 * <p>NullPointerException should be thrown.</p>
	 */
	public void testAddNestedHierarchy1() {
		try {
			hierarchy.addNestedHierarchy(null);
			fail("NullPointerException should be thrown.");
		} catch (NullPointerException e) {
			// pass
		}
	}

	/**
	 * Test <code>addNestedHierarchy</code> with a templateHierarchy which has
	 * been added.
	 *
	 * <p>DuplicateObjectException should be thrown.</p>
	 */
	public void testAddNestedHierarchy2() {
		try {
			hierarchy.addNestedHierarchy(sub_hierarchy);
			fail("DuplicateObjectException should be thrown.");
		} catch (DuplicateObjectException e) {
			// pass
		}
	}
	
	/**
	 * Test <code>addNestedHierarchy</code> with a templateHierarchy whose parent
	 * is not this object.
	 *
	 * <p>IllegalArgumentException should be thrown.</p>
	 */
	public void testAddNestedHierarchy3() {
		sub_hierarchy = new TemplateHierarchy(999, "name", 1000);
		
		try {
			hierarchy.addNestedHierarchy(sub_hierarchy);
			fail("IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}

}
