/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.impl;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewApplicationProcessorConfigurationException;
import com.cronos.onlinereview.review.selection.ReviewApplicationProcessorException;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockApplicationsManager;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockProjectManager;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockReviewerStatisticsManager;
import com.cronos.onlinereview.review.selection.impl.DefaultReviewApplicationProcessor;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.project.ReviewApplication;

/**
 * Failure test for DefaultReviewApplicationProcessor class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultReviewApplicationProcessorFailureTests extends TestCase {
	/**
	 * The DefaultReviewApplicationProcessor instance used in test.
	 */
	private DefaultReviewApplicationProcessor tested;

	/**
	 * The instance of ConfigurationObject used in test.
	 */
	private ConfigurationObject config;

	/**
	 * Set up for each test.
	 */
	protected void setUp() throws Exception {
		tested = new DefaultReviewApplicationProcessor();
		config = createConfigurationObject();
		tested.configure(config);
	}

	/**
	 * Test configure(ConfigurationObject config). When config is null.
	 */
	public void testConfigure_ConfigIsNull() {
		try {
			tested.configure(null);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewerStatisticsManager is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewerStatisticsManagerIsMissed() throws Exception {
		config.removeChild("reviewerStatisticsManager");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewApplicationProcessorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewerStatisticsManager is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewerStatisticsManagerIsInvalid() throws Exception {
		config.removeChild("reviewerStatisticsManager");
		config.addChild(createObject("reviewerStatisticsManager", MockApplicationsManager.class, null, null));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewApplicationProcessorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When projectManager is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ProjectManagerIsMissed() throws Exception {
		config.removeChild("projectManager");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewApplicationProcessorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When projectManager is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ProjectManagerIsInvalid() throws Exception {
		config.removeChild("projectManager");
		config.addChild(createObject("projectManager", MockApplicationsManager.class, null, null));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewApplicationProcessorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When primaryEligibilityPoints is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_PrimaryEligibilityPointsIsInvalidType() throws Exception {
		config.getChild("primaryEligibilityPoints").getChild("params").getChild("param1").setPropertyValue("value",
		    "-1");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewApplicationProcessorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When primaryEligibilityPoints is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_PrimaryEligibilityPointsIsInvalid() throws Exception {
		config.getChild("primaryEligibilityPoints").getChild("params").getChild("param1").setPropertyValue("value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewApplicationProcessorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test updateReviewAssignmentStatistics(ReviewApplication[] applications). When applications is null.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateReviewAssignmentStatistics_ApplicationsIsNull() throws Exception {
		try {
			tested.updateReviewAssignmentStatistics(null);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateReviewAssignmentStatistics(ReviewApplication[] applications). When applications is empty.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateReviewAssignmentStatistics_ApplicationsIsEmpty() throws Exception {
		try {
			tested.updateReviewAssignmentStatistics(new ReviewApplication[0]);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateReviewAssignmentStatistics(ReviewApplication[] applications). When applications has null.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateReviewAssignmentStatistics_ApplicationsHasNull() throws Exception {
		try {
			tested.updateReviewAssignmentStatistics(new ReviewApplication[] { null });
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateReviewAssignmentStatistics(ReviewApplication[] applications). When applications has different id.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateReviewAssignmentStatistics_ApplicationsHasDifferentId() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[2];
		applications[0] = new ReviewApplication();
		applications[0].setId(1);

		applications[1] = new ReviewApplication();
		applications[1].setId(2);

		try {
			tested.updateReviewAssignmentStatistics(applications);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateReviewAssignmentStatistics(ReviewApplication[] applications). When not configured
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateReviewAssignmentStatistics_NotConfigured() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[1];
		applications[0] = new ReviewApplication();
		applications[0].setId(1);
		applications[0].setReviewerId(1);

		try {
			new DefaultReviewApplicationProcessor().updateReviewAssignmentStatistics(applications);
			fail("Cannot go here.");
		} catch (IllegalStateException e) {
			// OK
		}
	}

	/**
	 * Test updateUnassignedReviewersStatistics(ReviewApplication[] applications). When applications is null.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateUnassignedReviewersStatistics_ApplicationsIsNull() throws Exception {
		try {
			tested.updateUnassignedReviewersStatistics(null);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateUnassignedReviewersStatistics(ReviewApplication[] applications). When applications is empty.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateUnassignedReviewersStatistics_ApplicationsIsEmpty() throws Exception {
		try {
			tested.updateUnassignedReviewersStatistics(new ReviewApplication[0]);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateUnassignedReviewersStatistics(ReviewApplication[] applications). When applications has null.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateUnassignedReviewersStatistics_ApplicationsHasNull() throws Exception {
		try {
			tested.updateUnassignedReviewersStatistics(new ReviewApplication[] { null });
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateUnassignedReviewersStatistics(ReviewApplication[] applications). When applications has different id.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateUnassignedReviewersStatistics_ApplicationsHasDifferentId() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[2];
		applications[0] = new ReviewApplication();
		applications[0].setId(1);

		applications[1] = new ReviewApplication();
		applications[1].setId(2);

		try {
			tested.updateUnassignedReviewersStatistics(applications);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test updateUnassignedReviewersStatistics(ReviewApplication[] applications). When not configured
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateUnassignedReviewersStatistics_NotConfigured() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[1];
		applications[0] = new ReviewApplication();
		applications[0].setId(1);
		applications[0].setReviewerId(1);

		try {
			new DefaultReviewApplicationProcessor().updateUnassignedReviewersStatistics(applications);
			fail("Cannot go here.");
		} catch (IllegalStateException e) {
			// OK
		}
	}

	/**
	 * Test updateUnassignedReviewersStatistics(ReviewApplication[] applications). When not configured
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateUnassignedReviewersStatistics_ManageError() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[1];
		applications[0] = new ReviewApplication();
		applications[0].setId(1);
		applications[0].setReviewerId(1);
		applications[0].setAcceptPrimary(true);

		try {
			tested.updateUnassignedReviewersStatistics(applications);
			fail("Cannot go here.");
		} catch (ReviewApplicationProcessorException e) {
			// OK
		}
	}

	/**
	 * Creates ConfigurationObject for listener.
	 * 
	 * @return the create object.
	 * @throws Exception
	 *             to jUnit.
	 */
	static ConfigurationObject createConfigurationObject() throws Exception {
		ConfigurationObject objectFactoryConfig = new DefaultConfigurationObject("processor_config");

		objectFactoryConfig.addChild(createObject("reviewerStatisticsManager", MockReviewerStatisticsManager.class,
		    null, null));
		objectFactoryConfig.addChild(createObject("projectManager", MockProjectManager.class, null, null));

		objectFactoryConfig.addChild(createObject("loggerName", String.class, new String[] { "String" },
		    new String[] { "failuretests.listener" }));
		objectFactoryConfig.addChild(createObject("primaryEligibilityPoints", Double.class, new String[] { "double" },
		    new String[] { "2.0" }));

		return objectFactoryConfig;
	}

	/**
	 * Creates ConfigurationObject for object.
	 * 
	 * @param name
	 *            the object name.
	 * @param zz
	 *            the class type.
	 * 
	 * @param types
	 *            the parameter types.
	 * 
	 * @param values
	 *            the parameters values.
	 * @return the create object.
	 * @throws Exception
	 *             to jUnit.
	 */
	private static ConfigurationObject createObject(String name, Class zz, String[] types, String[] values)
	    throws Exception {
		ConfigurationObject objDef = new DefaultConfigurationObject(name);
		objDef.setPropertyValue("type", zz.getName());

		ConfigurationObject params = new DefaultConfigurationObject("params");
		if (types != null) {
			for (int i = 0; i < types.length; i++) {
				ConfigurationObject param = new DefaultConfigurationObject("param" + (i + 1));
				param.setPropertyValue("type", types[i]);
				param.setPropertyValue("value", values[i]);
				params.addChild(param);
			}
		}

		objDef.addChild(params);
		return objDef;
	}
}