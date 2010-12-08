/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.impl;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewSelectionConfigurationException;
import com.cronos.onlinereview.review.selection.failuretests.impl.workloadfactorcalculator.DefaultWorkloadFactorCalculatorFailureTests;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockApplicationsManager;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockPhaseManager;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockProjectManager;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockReviewerStatisticsManager;
import com.cronos.onlinereview.review.selection.impl.DefaultReviewApplicationProcessor;
import com.cronos.onlinereview.review.selection.impl.RatingBasedSelectionAlgorithm;
import com.cronos.onlinereview.review.selection.impl.workloadfactorcalculator.DefaultWorkloadFactorCalculator;
import com.topcoder.apps.review.rboard.RBoardApplicationBean;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.project.ReviewApplication;

/**
 * Failure test for RatingBasedSelectionAlgorithm class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RatingBasedSelectionAlgorithmFailureTests extends TestCase {
	/**
	 * The RatingBasedSelectionAlgorithm instance used in test.
	 */
	private RatingBasedSelectionAlgorithm tested;

	/**
	 * The instance of ConfigurationObject used in test.
	 */
	private ConfigurationObject config;

	/**
	 * Set up for each test.
	 */
	protected void setUp() throws Exception {
		tested = new RatingBasedSelectionAlgorithm();
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
		} catch (ReviewSelectionConfigurationException e) {
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
		} catch (ReviewSelectionConfigurationException e) {
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
		} catch (ReviewSelectionConfigurationException e) {
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
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When phaseManager is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_PhaseManagerIsMissed() throws Exception {
		config.removeChild("phaseManager");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When phaseManager is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_PhaseManagerIsInvalid() throws Exception {
		config.removeChild("phaseManager");
		config.addChild(createObject("phaseManager", MockApplicationsManager.class, null, null));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewApplicationProcessor is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewApplicationProcessorIsMissed() throws Exception {
		config.removeChild("reviewApplicationProcessor");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewApplicationProcessor is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewApplicationProcessorIsInvalid() throws Exception {
		config.removeChild("reviewApplicationProcessor");
		config.addChild(createObject("reviewApplicationProcessor", MockApplicationsManager.class, null, null));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When rboardApplicationBean is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RboardApplicationBeanIsMissed() throws Exception {
		config.removeChild("rboardApplicationBean");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When rboardApplicationBean is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RboardApplicationBeanIsInvalid() throws Exception {
		config.removeChild("rboardApplicationBean");
		config.addChild(createObject("rboardApplicationBean", MockApplicationsManager.class, null, null));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When workloadFactorCalculator is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_WorkloadFactorCalculatorIsMissed() throws Exception {
		config.removeChild("workloadFactorCalculator");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When workloadFactorCalculator is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_WorkloadFactorCalculatorIsInvalid() throws Exception {
		config.removeChild("workloadFactorCalculator");
		config.addChild(createObject("workloadFactorCalculator", MockApplicationsManager.class, null, null));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When timelineReliabilitySubtractor is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_TimelineReliabilitySubtractorIsInvalidType() throws Exception {
		config.getChild("timelineReliabilitySubtractor").getChild("params").getChild("param1").setPropertyValue(
		    "value", "-1");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When timelineReliabilitySubtractor is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_TimelineReliabilitySubtractorIsInvalid() throws Exception {
		config.getChild("timelineReliabilitySubtractor").getChild("params").getChild("param1").setPropertyValue(
		    "value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When rboardDataSourceName is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RboardDataSourceNameIsInvalidType() throws Exception {
		config.getChild("rboardDataSourceName").getChild("params").getChild("param1").setPropertyValue("value", 1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When rboardDataSourceName is missed.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RboardDataSourceNameIsMissed() throws Exception {
		config.removeChild("rboardDataSourceName");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When rboardDataSourceName is empty.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RboardDataSourceNameIsEmpty() throws Exception {
		config.getChild("rboardDataSourceName").getChild("params").getChild("param1").setPropertyValue("value", " ");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When primaryReviewerResponsibilityId is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_PrimaryReviewerResponsibilityIdIsInvalidType() throws Exception {
		config.getChild("primaryReviewerResponsibilityId").getChild("params").getChild("param1").setPropertyValue(
		    "value", "-1");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When primaryReviewerResponsibilityId is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_PrimaryReviewerResponsibilityIdIsInvalid() throws Exception {
		config.getChild("primaryReviewerResponsibilityId").getChild("params").getChild("param1").setPropertyValue(
		    "value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewerResponsibilityId is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewerResponsibilityIdIsInvalidType() throws Exception {
		config.getChild("reviewerResponsibilityId").getChild("params").getChild("param1").setPropertyValue("value",
		    "-1");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewerResponsibilityId is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewerResponsibilityIdIsInvalid() throws Exception {
		config.getChild("reviewerResponsibilityId").getChild("params").getChild("param1").setPropertyValue("value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewPhaseName is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewPhaseNameIsInvalidType() throws Exception {
		config.getChild("reviewPhaseName").getChild("params").getChild("param1").setPropertyValue("value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewPhaseName is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewPhaseNameIsInvalid() throws Exception {
		config.getChild("reviewPhaseName").getChild("params").getChild("param1").setPropertyValue("value", " ");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When secondaryReviewersNumber is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_SecondaryReviewersNumberIsInvalidType() throws Exception {
		config.getChild("secondaryReviewersNumber").getChild("params").getChild("param1").setPropertyValue("value",
		    "-1");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When secondaryReviewersNumber is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_SecondaryReviewersNumberIsInvalid() throws Exception {
		config.getChild("secondaryReviewersNumber").getChild("params").getChild("param1").setPropertyValue("value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewSelectionConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test selectReviewers(ReviewApplication[] applications). When applications is null.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testSelectReviewers_ApplicationsIsNull() throws Exception {
		try {
			tested.selectReviewers(null);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test selectReviewers(ReviewApplication[] applications). When applications is empty.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testSelectReviewers_ApplicationsIsEmpty() throws Exception {
		try {
			tested.selectReviewers(new ReviewApplication[0]);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test selectReviewers(ReviewApplication[] applications). When applications has null.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testSelectReviewers_ApplicationsHasNull() throws Exception {
		try {
			tested.selectReviewers(new ReviewApplication[] { null });
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test selectReviewers(ReviewApplication[] applications). When applications has different id.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testSelectReviewers_ApplicationsHasDifferentId() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[3];
		applications[0] = new ReviewApplication();
		applications[0].setProjectId(1);
		applications[0].setReviewerId(1);
		applications[0].setAcceptPrimary(true);

		applications[1] = new ReviewApplication();
		applications[1].setProjectId(2);
		applications[1].setReviewerId(2);

		applications[2] = new ReviewApplication();
		applications[2].setProjectId(1);
		applications[2].setReviewerId(3);

		try {
			tested.selectReviewers(applications);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test selectReviewers(ReviewApplication[] applications). When not configured
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testSelectReviewers_NotConfigured() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[3];
		applications[0] = new ReviewApplication();
		applications[0].setId(1);
		applications[0].setReviewerId(1);
		applications[0].setAcceptPrimary(true);

		applications[1] = new ReviewApplication();
		applications[1].setId(1);
		applications[1].setReviewerId(2);

		applications[2] = new ReviewApplication();
		applications[2].setId(1);
		applications[2].setReviewerId(3);

		try {
			new RatingBasedSelectionAlgorithm().selectReviewers(applications);
			fail("Cannot go here.");
		} catch (IllegalStateException e) {
			// OK
		}
	}

	/**
	 * Test selectReviewers(ReviewApplication[] applications). When manager throw exception.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testUpdateUnassignedReviewersStatistics_ApplicationsHasDifferentId() throws Exception {
		ReviewApplication[] applications = new ReviewApplication[3];
		applications[0] = new ReviewApplication();
		applications[0].setProjectId(1);
		applications[0].setReviewerId(1);
		applications[0].setAcceptPrimary(true);

		applications[1] = new ReviewApplication();
		applications[1].setProjectId(2);
		applications[1].setReviewerId(2);

		applications[2] = new ReviewApplication();
		applications[2].setProjectId(1);
		applications[2].setReviewerId(3);

		try {
			tested.selectReviewers(applications);
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
	 * Creates ConfigurationObject for listener.
	 * 
	 * @return the create object.
	 * @throws Exception
	 *             to jUnit.
	 */
	static ConfigurationObject createConfigurationObject() throws Exception {
		ConfigurationObject objectFactoryConfig = new DefaultConfigurationObject("selection_algorithm_config");

		objectFactoryConfig.addChild(createObject("reviewerStatisticsManager", MockReviewerStatisticsManager.class,
		    null, null));
		objectFactoryConfig.addChild(createObject("projectManager", MockProjectManager.class, null, null));
		objectFactoryConfig.addChild(createObject("phaseManager", MockPhaseManager.class, null, null));
		objectFactoryConfig.addChild(createObject("reviewApplicationProcessor",
		    DefaultReviewApplicationProcessor.class, null, null));
		objectFactoryConfig.addChild(createObject("rboardApplicationBean", RBoardApplicationBean.class, null, null));
		objectFactoryConfig.addChild(createObject("workloadFactorCalculator", DefaultWorkloadFactorCalculator.class,
		    null, null));

		objectFactoryConfig.addChild(createObject("loggerName", String.class, new String[] { "String" },
		    new String[] { "failuretests.listener" }));
		objectFactoryConfig.addChild(createObject("timelineReliabilitySubtractor", Double.class,
		    new String[] { "double" }, new String[] { "0.5" }));
		objectFactoryConfig.addChild(createObject("rboardDataSourceName", String.class, new String[] { "String" },
		    new String[] { "rb" }));
		objectFactoryConfig.addChild(createObject("primaryReviewerResponsibilityId", Integer.class,
		    new String[] { "int" }, new String[] { "5" }));
		objectFactoryConfig.addChild(createObject("reviewerResponsibilityId", Integer.class, new String[] { "int" },
		    new String[] { "4" }));
		objectFactoryConfig.addChild(createObject("reviewPhaseName", String.class, new String[] { "String" },
		    new String[] { "review" }));
		objectFactoryConfig.addChild(createObject("secondaryReviewersNumber", Integer.class, new String[] { "int" },
		    new String[] { "2" }));

		for (ConfigurationObject child : DefaultReviewApplicationProcessorFailureTests.createConfigurationObject()
		    .getAllChildren()) {
			if (!objectFactoryConfig.containsChild(child.getName())) {
				objectFactoryConfig.addChild(child);
			}
		}
		for (ConfigurationObject child : DefaultWorkloadFactorCalculatorFailureTests.createConfigurationObject()
		    .getAllChildren()) {
			if (!objectFactoryConfig.containsChild(child.getName())) {
				objectFactoryConfig.addChild(child);
			}
		}

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