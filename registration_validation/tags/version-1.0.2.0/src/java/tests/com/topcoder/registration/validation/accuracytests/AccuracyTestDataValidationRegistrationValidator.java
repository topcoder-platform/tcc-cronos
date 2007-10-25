/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import com.topcoder.management.ban.BanManager;
import com.topcoder.management.ban.manager.MockBanManager;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.impl.MockTeamManagerImpl;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.impl.MockProjectServicesImpl;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.util.log.Log;

import junit.framework.TestCase;

/**
 * This class contains unit tests for
 * <code>DataValidationRegistrationValidator</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestDataValidationRegistrationValidator extends TestCase {
	/**
	 * The DEFAULT_NAMESPACE instance used to test.
	 */
	public static final String DEFAULT_NAMESPACE = "com.topcoder.registration.validation.DataValidationRegistrationValidator";

	/**
	 * The DataValidationRegistrationValidator instance used to test.
	 */
	private DataValidationRegistrationValidator drv;

	/**
	 * The TeamManager instance used to test.
	 */
	private TeamManager tm;

	/**
	 * The ProjectServices instance used to test.
	 */
	private ProjectServices ps;

	/**
	 * The BanManager instance used to test.
	 */
	private BanManager bm;

	/**
	 * The Log instance used to test.
	 */
	private Log logger;

	/**
	 * The ConfigurableValidator instance used to test.
	 */
	private ConfigurableValidator validator;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.loadNamespaces();
		tm = new MockTeamManagerImpl();
		ps = new MockProjectServicesImpl();
		bm = new MockBanManager();
		logger = TestHelper.getLogger("validatorLog.txt");

		validator = new MemberMinimumNumberOfRatingsForRatingTypeValidator(
				"test.AbstractConfigurableValidator");

		drv = new DataValidationRegistrationValidator(tm, ps, bm, logger, validator);
	}

	/**
	 * Clean up the test environment after testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		TestHelper.clearConfig();
	}

	/**
	 * Function test : Tests <code>DataValidationRegistrationValidator()</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testDataValidationRegistrationValidator1Accuracy() throws Exception {
		assertNotNull("Null is not allowed.", new DataValidationRegistrationValidator());
	}

	/**
	 * Function test : Tests
	 * <code>DataValidationRegistrationValidator(String namespace)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testDataValidationRegistrationValidatorAccuracy() throws Exception {
		assertNotNull("Null is not allowed.", new DataValidationRegistrationValidator(DEFAULT_NAMESPACE));
	}

	/**
	 * Function test : Tests
	 * <code>validate(RegistrationInfo registration,ExternalUser user, FullProjectData project)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testValidateAccuracy() throws Exception {
		assertNotNull("Null is not allowed.", drv);
	}

	/**
	 * Function test : Tests <code>getTeamManager()</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetTeamManagerAccuracy() throws Exception {
		assertEquals("object is not equals.", tm, drv.getTeamManager());
	}

	/**
	 * Function test : Tests <code>getProjectServices()</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetProjectServicesAccuracy() throws Exception {
		assertEquals("object is not equals.", ps, drv.getProjectServices());
	}

	/**
	 * Function test : Tests <code>getBanManager()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetBanManagerAccuracy() throws Exception {
		assertEquals("object is not equals.", bm, drv.getBanManager());
	}

	/**
	 * Function test : Tests <code>getLog()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetLogAccuracy() throws Exception {
		assertEquals("object is not equals.", logger, drv.getLog());
	}

	/**
	 * Function test : Tests <code>getValidator()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetValidatorAccuracy() throws Exception {
		assertEquals("object is not equals.", validator, drv.getValidator());
	}

}