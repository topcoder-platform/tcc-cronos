/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;

import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.validators.simple.ProjectInPhaseValidator;
import com.topcoder.util.datavalidator.BundleInfo;

/**
 * This class contains unit tests for <code>ProjectInPhaseValidator</code>
 * class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestProjectInPhaseValidator extends TestCase {
	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The ProjectInPhaseValidator instance used to test.
	 */
	private ProjectInPhaseValidator validator = null;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.loadNamespaces();
		validator = new ProjectInPhaseValidator("ProjectInPhaseValidator.ns");
		bundleInfo = validator.getBundleInfo();
		AccuracyTestHelper.setLog(validator);
	}

	/**
	 * Clean up the test environment after testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		AccuracyTestHelper.clearNamespaces();
	}

	/**
	 * Function test : Tests
	 * <code>ProjectInPhaseValidator(BundleInfo bundleInfo,
	 * int minimumNumRatings, RatingType ratingType)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testProjectInPhaseValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new ProjectInPhaseValidator(bundleInfo, 1));
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy, numRatings >= this.minimumNumRatings
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessageAccuracy1() throws Exception {
		ValidationInfo info = AccuracyTestHelper.createValidationInfo();
		FullProjectData project = info.getProject();
		PhaseStatus openPhaseStatus = PhaseStatus.OPEN;
		long phaseLength = 7;
		Phase phase = new Phase(project, phaseLength);
		phase.setPhaseStatus(openPhaseStatus);
		phase.setId(3);
		project.clearPhases();
		project.addPhase(phase);

		String msg = validator.getMessage(info);
		assertNull("Null is expected.", msg);
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy, numRatings < this.minimumNumRatings
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessageAccuracy2() throws Exception {
		validator = new ProjectInPhaseValidator(bundleInfo, 3);
		AccuracyTestHelper.setLog(validator);
		String msg = validator.getMessage(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Null is allowed.", msg);
	}
}