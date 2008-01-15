/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.accuracytests;

import com.topcoder.controlpanel.clientassociations.ClientAssociationServiceStatelessSessionBeanAccuracyTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author restarter
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

	/**
	 * <p>
	 * This test case aggregates accuracy unit tests for the component.
	 * </p>
	 *
	 * @return the accuracy test suite for the component
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTestSuite(ClientAssociationDAOExceptionAccuracyTest.class);
		suite.addTestSuite(ClientAssociationHibernateDAOAccuracyTest.class);
		suite.addTestSuite(ClientAssociationServiceExceptionAccuracyTest.class);
		suite
				.addTestSuite(ClientAssociationServiceStatelessSessionBeanAccuracyTest.class);
		suite.addTestSuite(HibernateHelperAccuracyTest.class);
		suite.addTestSuite(ClientAssociationServiceContainerTest.class);
		return suite;
	}

}
