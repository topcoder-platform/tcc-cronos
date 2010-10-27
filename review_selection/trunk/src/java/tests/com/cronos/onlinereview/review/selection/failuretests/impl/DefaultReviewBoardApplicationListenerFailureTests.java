/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.impl;

import java.util.Date;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewBoardApplicationListenerConfigurationException;
import com.cronos.onlinereview.review.selection.ReviewBoardApplicationListenerException;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockApplicationsManager;
import com.cronos.onlinereview.review.selection.failuretests.mock.MockPhaseManager;
import com.cronos.onlinereview.review.selection.impl.DefaultReviewBoardApplicationListener;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.management.project.ReviewApplication;

/**
 * Failure test for DefaultReviewBoardApplicationListener class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultReviewBoardApplicationListenerFailureTests extends TestCase {
	/**
	 * The DefaultReviewBoardApplicationListener instance used in test.
	 */
	private DefaultReviewBoardApplicationListener tested;

	/**
	 * The instance of ConfigurationObject used in test.
	 */
	private ConfigurationObject config;

	/**
	 * Set up for each test.
	 */
	protected void setUp() throws Exception {
		tested = new DefaultReviewBoardApplicationListener();
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
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
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
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When ReviewApplicationsManager is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewApplicationsManagerIsMissed() throws Exception {
		config.removeChild("reviewApplicationsManager");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When ReviewApplicationsManager is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewApplicationsManagerIsInvalid() throws Exception {
		config.removeChild("reviewApplicationsManager");
		config.addChild(createObject("ReviewApplicationsManager", MockPhaseManager.class, null, null));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewersNumber is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewersNumberIsInvalidType() throws Exception {
		config.getChild("reviewersNumber").getChild("params").getChild("param1").setPropertyValue("value", "-1");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewersNumber is invalid.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_ReviewersNumberIsInvalid() throws Exception {
		config.getChild("reviewersNumber").getChild("params").getChild("param1").setPropertyValue("value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When registrationPhaseTypeName is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RegistrationPhaseTypeNameIsInvalidType() throws Exception {
		config.getChild("registrationPhaseTypeName").getChild("params").getChild("param1")
		    .setPropertyValue("value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When registrationPhaseTypeName is empty.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RegistrationPhaseTypeNameIsEmpty() throws Exception {
		config.getChild("registrationPhaseTypeName").getChild("params").getChild("param1").setPropertyValue("value",
		    " ");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When registrationPhaseOperator is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RegistrationPhaseOperatorIsMissed() throws Exception {
		config.removeChild("registrationPhaseOperator");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When registrationPhaseOperator is invalid type.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RegistrationPhaseOperatorIsInvalidType() throws Exception {
		config.getChild("registrationPhaseOperator").getChild("params").getChild("param1")
		    .setPropertyValue("value", -1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When registrationPhaseOperator is empty.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testConfigure_RegistrationPhaseOperatorIsEmpty() throws Exception {
		config.getChild("registrationPhaseOperator").getChild("params").getChild("param1").setPropertyValue("value",
		    " ");
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test applicationReceived(ReviewApplication application). When application is null.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testApplicationReceived_ApplicationIsNull() throws Exception {
		try {
			tested.applicationReceived(null);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}

	/**
	 * Test applicationReceived(ReviewApplication application). When not configurated.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testApplicationReceived_IllegalStateException() throws Exception {
		try {
			new DefaultReviewBoardApplicationListener().applicationReceived(new ReviewApplication());
			fail("Cannot go here.");
		} catch (IllegalStateException e) {
			// OK
		}
	}

	/**
	 * Test applicationReceived(ReviewApplication application). When exception thrown from manager.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	public void testApplicationReceived_ManagerError() throws Exception {
		ReviewApplication application = new ReviewApplication();
		application.setId(1);
		application.setProjectId(1);
		application.setReviewerId(1);
		application.setApplicationDate(new Date());
		try {
			tested.applicationReceived(application);
			fail("Cannot go here.");
		} catch (ReviewBoardApplicationListenerException e) {
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
		ConfigurationObject objectFactoryConfig = new DefaultConfigurationObject("listener_config");

		objectFactoryConfig.addChild(createObject("phaseManager", MockPhaseManager.class, null, null));
		objectFactoryConfig.addChild(createObject("reviewApplicationsManager", MockApplicationsManager.class, null,
		    null));

		objectFactoryConfig.addChild(createObject("loggerName", String.class, new String[] { "String" },
		    new String[] { "failuretests.listener" }));
		objectFactoryConfig.addChild(createObject("reviewersNumber", Integer.class, new String[] { "int" },
		    new String[] { "3" }));
		objectFactoryConfig.addChild(createObject("registrationPhaseTypeName", String.class, new String[] { "String" },
		    new String[] { "review registration" }));
		objectFactoryConfig.addChild(createObject("registrationPhaseOperator", String.class, new String[] { "String" },
		    new String[] { "admin" }));

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