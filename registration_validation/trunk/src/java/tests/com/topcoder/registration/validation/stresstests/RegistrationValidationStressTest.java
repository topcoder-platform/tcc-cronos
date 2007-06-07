/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.stresstests;

import java.io.FileOutputStream;
import java.io.PrintStream;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.RatingType;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.validators.conditional.ProjectTypeConditionalValidator;
import com.topcoder.registration.validation.validators.simple.MemberMustBeRegisteredValidator;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.basic.BasicLogFactory;

import junit.framework.TestCase;

/**
 * <p>
 * This class is the stress test for Registration Validation version 1.0.
 * </p>
 *
 * @author Hacker_QC
 * @version 1.0
 */
public class RegistrationValidationStressTest extends TestCase {

	/**
	 * The loop count for stress test.
	 */
	private static final int COUNT = 100;

	/**
	 * The name of config file used in stress test.
	 */
	private static String CONFIG_FILE1 = "stresstests\\TestDemoConfig.xml";

	/**
	 * The name of config file used in stress test.
	 */
	private static String CONFIG_FILE2 = "stresstests\\Document_Generator.xml";

	/**
	 * The name of config file used in stress test.
	 */
	private static String CONFIG_FILE3 = "stresstests\\MemberMinimumRatingForRatingTypeValidator.xml";

	/**
	 * The namespace used in the config.G
	 */
	private static String CONFIG_NAMESPACE = "testDemoConfig";

	/**
	 * <p>
	 * Sets up test environment.
	 * </p>
	 *
	 * @throws Exception
	 *             to JUnit
	 */
	protected void setUp() throws Exception {
		StressHelper.clearConfig();
		StressHelper.loadConfig(CONFIG_FILE1);
		StressHelper.loadConfig(CONFIG_FILE2);
		StressHelper.loadConfig(CONFIG_FILE3);
		PrintStream ps = new PrintStream(new FileOutputStream("ComponentDemoLog.txt", true), true);
		LogManager.setLogFactory(new BasicLogFactory(ps));
	}

	/**
	 * <p>
	 * Clears the testing environment.
	 * </p>
	 *
	 * @throws Exception
	 *             when error occurs
	 */
	protected void tearDown() throws Exception {
		StressHelper.clearConfig();
	}
	
	/**
	 * <p>
	 * This method tests the functionality of creating validator with give namespace in high
	 * stress.
	 * </p>
	 *
	 * @throws Exception
	 *             if any error occurs.
	 */
	public void testStressForCreatingValidator() throws Exception {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 10 * COUNT; ++i) {
			DataValidationRegistrationValidator registrationValidator = new DataValidationRegistrationValidator(
					CONFIG_NAMESPACE);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("The stress test for creating validator costs: "
				+ (endTime - startTime) + " milliseconds.");
	}

	/**
	 * <p>
	 * This method tests the functionality of validating info and user with too small rating in high
	 * stress.
	 * </p>
	 *
	 * @throws Exception
	 *             if any error occurs.
	 */
	public void testStressForSmallRating() throws Exception {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < COUNT; ++i) {
			DataValidationRegistrationValidator registrationValidator = new DataValidationRegistrationValidator(
					CONFIG_NAMESPACE);

			FullProjectData project = StressHelper.getProject();
			RegistrationInfo info1 = new RegistrationInfoImpl();
			info1.setProjectId(2);
			info1.setRoleId(3);
			info1.setUserId(2);
			RatingInfo ratingInfo1 = new RatingInfo(RatingType.DESIGN, 900, 5, 20);
			ExternalUser user1 = new ExternalUserImpl(1, "handle1", "user1", "TCMember",
					"user1@topcoder.com");
			((ExternalUserImpl) user1).addRatingInfo(ratingInfo1);

			OperationResult result = registrationValidator.validate(info1, user1, project);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("The stress test for validation with small rating costs: "
				+ (endTime - startTime) + " milliseconds.");
	}

	/**
	 * <p>
	 * This method tests the functionality of validating registered user in high stress.
	 * </p>
	 *
	 * @throws Exception
	 *             if any error occurs.
	 */
	public void testStressForRegisteredUser() throws Exception {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 10 * COUNT; ++i) {
			DataValidationRegistrationValidator registrationValidator = new DataValidationRegistrationValidator(
					CONFIG_NAMESPACE);

			FullProjectData project = StressHelper.getProject();

			RegistrationInfo info1 = new RegistrationInfoImpl();
			info1.setProjectId(2);
			info1.setRoleId(3);
			info1.setUserId(2);
			RatingInfo ratingInfo1 = new RatingInfo(RatingType.DESIGN, 900, 5, 20);
			ExternalUser user1 = new ExternalUserImpl(1, "handle1", "user1", "TCMember",
					"user1@topcoder.com");
			((ExternalUserImpl) user1).addRatingInfo(ratingInfo1);

			RatingInfo ratingInfo2 = new RatingInfo(RatingType.DESIGN, 2000, 5, 20);
			ExternalUser user2 = new ExternalUserImpl(2, "handle2", "user2", "TCMember",
					"user2@topcoder.com");
			((ExternalUserImpl) user2).addRatingInfo(ratingInfo2);

			RegistrationInfo info2 = new RegistrationInfoImpl();
			info2.setProjectId(2);
			info2.setRoleId(3);
			info2.setUserId(1);
			OperationResult result = registrationValidator.validate(info2, user2, project);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("The stress test for validation with registered user costs: "
				+ (endTime - startTime) + " milliseconds.");
	}

	/**
	 * <p>
	 * This method tests the functionality of validating valid info and user in high stress.
	 * </p>
	 *
	 * @throws Exception
	 *             if any error occurs.
	 */
	public void testStressForSuccess() throws Exception {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 100 * COUNT; ++i) {
			DataValidationRegistrationValidator registrationValidator = new DataValidationRegistrationValidator(
					CONFIG_NAMESPACE);

			FullProjectData project = StressHelper.getProject();

			RegistrationInfo info3 = new RegistrationInfoImpl();
			info3.setProjectId(2);
			info3.setRoleId(3);
			info3.setUserId(3);
			RatingType ratingType3 = RatingType.DESIGN;
			RatingInfo ratingInfo3 = new RatingInfo(RatingType.DESIGN, 1345, 5, 20);
			ExternalUser user3 = new ExternalUserImpl(3, "handle3", "user3", "TCMember",
					"user3@topcoder.com");
			((ExternalUserImpl) user3).addRatingInfo(ratingInfo3);

			OperationResult result = registrationValidator.validate(info3, user3, project);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("The stress test for validation successfully costs: " + (endTime - startTime)
				+ " milliseconds.");
	}

	/**
	 * <p>
	 * This method tests the functionality of creating custom validator in high stress.
	 * </p>
	 *
	 * @throws Exception
	 *             if any error occurs.
	 */
	public void testStressForCreatingCustomValidator() throws Exception {
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 100 * COUNT; ++i) {
			BundleInfo bundleInfo = new BundleInfo();
			bundleInfo.setBundle("myBundle");
            bundleInfo.setMessageKey("messageKey");
			bundleInfo.setDefaultMessage("myTemplate.txt");

			MemberMustBeRegisteredValidator innerValidator = new MemberMustBeRegisteredValidator(
					bundleInfo);

			ProjectTypeConditionalValidator validator = new ProjectTypeConditionalValidator(
					innerValidator, 5);
		}

		long endTime = System.currentTimeMillis();
		System.out.println("The stress test for creating custom validator costs: "
				+ (endTime - startTime) + " milliseconds.");
	}
}
