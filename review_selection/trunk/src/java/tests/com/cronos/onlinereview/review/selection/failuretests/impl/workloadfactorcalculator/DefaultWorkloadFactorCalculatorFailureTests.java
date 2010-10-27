/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.failuretests.impl.workloadfactorcalculator;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.impl.WorkloadFactorCalculatorConfigurationException;
import com.cronos.onlinereview.review.selection.impl.workloadfactorcalculator.DefaultWorkloadFactorCalculator;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;

/**
 * Failure test for DefaultWorkloadFactorCalculator class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultWorkloadFactorCalculatorFailureTests extends TestCase {
	/**
	 * The instance of DefaultWorkloadFactorCalculator used in test.
	 */
	private DefaultWorkloadFactorCalculator tested;

	/**
	 * The ConfigurationObject instance used in test.
	 */
	private ConfigurationObject config;

	/**
	 * Set up for each test.
	 * 
	 * @throws Exception
	 *             to jUnit.
	 */
	protected void setUp() throws Exception {
		tested = new DefaultWorkloadFactorCalculator();
		config = createConfigurationObject();
	}

	/**
	 * Creates ConfigurationObject for listener.
	 * 
	 * @return the create object.
	 * @throws Exception
	 *             to jUnit.
	 */
	public static ConfigurationObject createConfigurationObject() throws Exception {
		ConfigurationObject objectFactoryConfig = new DefaultConfigurationObject("calculator_config");
		objectFactoryConfig.addChild(createObject("workloadFactorBase", Double.class, new String[] { "double" },
		    new String[] { "0.3" }));
		objectFactoryConfig.addChild(createObject("reviewNumberMultiplier", Double.class, new String[] { "double" },
		    new String[] { "0.4" }));
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
	 * Test configure(ConfigurationObject config). When workloadFactorBase is negative.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	public void testConfigure_WorkloadFactorBaseIsNegative() throws Exception {
		config.getChild("workloadFactorBase").getChild("params").getChild("param1").setPropertyValue("value", -0.1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (WorkloadFactorCalculatorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When workloadFactorBase is &gt; 1.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	public void testConfigure_WorkloadFactorBaseIsLarger() throws Exception {
		config.getChild("workloadFactorBase").getChild("params").getChild("param1").setPropertyValue("value", 1.1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (WorkloadFactorCalculatorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When workloadFactorBase is not double.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	public void testConfigure_WorkloadFactorBaseIsNotDouble() throws Exception {
		config.getChild("workloadFactorBase").getChild("params").getChild("param1").setPropertyValue("value", new Long(1));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (WorkloadFactorCalculatorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewNumberMultiplier is negative.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	public void testConfigure_ReviewNumberMultiplierIsNegative() throws Exception {
		config.getChild("reviewNumberMultiplier").getChild("params").getChild("param1").setPropertyValue("value", -0.1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (WorkloadFactorCalculatorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewNumberMultiplier is &gt; 1.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	public void testConfigure_ReviewNumberMultiplierIsLarger() throws Exception {
		config.getChild("reviewNumberMultiplier").getChild("params").getChild("param1").setPropertyValue("value", 1.1);
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (WorkloadFactorCalculatorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test configure(ConfigurationObject config). When reviewNumberMultiplier is not double.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	public void testConfigure_ReviewNumberMultiplierIsNotDouble() throws Exception {
		config.getChild("reviewNumberMultiplier").getChild("params").getChild("param1").setPropertyValue("value", new Long(1));
		try {
			tested.configure(config);
			fail("Cannot go here.");
		} catch (WorkloadFactorCalculatorConfigurationException e) {
			// OK
		}
	}

	/**
	 * Test calculateWorkloadFactor(int concurrentReviewNumber). When concurrentReviewNumber is negative.
	 * 
	 * @throws Exception
	 *             throw exception to JUnit.
	 */
	public void testCalculateWorkloadFactor_ConcurrentReviewNumberIsNegative() throws Exception {
		try {
			tested.calculateWorkloadFactor(-1);
			fail("Cannot go here.");
		} catch (IllegalArgumentException e) {
			// OK
		}
	}
}
