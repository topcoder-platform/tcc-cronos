/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import com.orpheus.game.AttributeScope;

import junit.framework.TestCase;

/**
 * Accuracy test case for AttributeScope.
 * 
 * @author Zulander
 * @version 1.0
 */
public class AttributeScopeAccuracyTest extends TestCase {

	/**
	 * Default AttributeScope instance used in the tests.
	 */
	private AttributeScope attributeScope;

	/**
	 * Attribute of default AttributeScope instance.
	 */
	private static String attr = "attr";

	/**
	 * Scope of default AttributeScope instance.
	 */
	private static String scope = "session";

	/**
	 * Set up. Creates instance of AttributeScope used in the tests.
	 * 
	 * @throws Exception
	 *             exception thrown to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		attributeScope = new AttributeScope(attr, scope);
	}

	/**
	 * Test for {@link AttributeScope#AttributeScope(String, String)}. No
	 * exception should be thrown.
	 */
	public void testAttributeScope_Constructor() {
		// success
	}

	/**
	 * Test for {@link AttributeScope#getAttribute()}. It should return a
	 * String equals attr.
	 */
	public void testAttributeScope_GetAttribute() {
		assertEquals("attribute is not set correctly.", attr, attributeScope
				.getAttribute());
	}

	/**
	 * Test for {@link AttributeScope#getScope()}. It should return a String
	 * equals scope.
	 */
	public void testAttributeScope_GetScope() {
		assertEquals("scope is not set correctly.", scope, attributeScope
				.getScope());
	}

}
