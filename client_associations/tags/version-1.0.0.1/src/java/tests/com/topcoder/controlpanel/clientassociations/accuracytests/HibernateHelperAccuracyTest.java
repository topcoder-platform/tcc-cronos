/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.accuracytests;

import junit.framework.TestCase;

import com.topcoder.controlpanel.clientassociations.dao.hibernate.HibernateHelper;

/**
 * <p>
 * Accuracy test cases for all <code>HibernateHelper</code>.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class HibernateHelperAccuracyTest extends TestCase {
	/**
	 * <p>
	 * Accuracy Tests for <code>getSessionFactory</code> method.
	 * </p>
	 */
	public void testGetSessionFactory() {
		assertNotNull("HibernateHelper is not initialized properly.",
				HibernateHelper.getSessionFactory());
	}
}
