/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.scheduler.Scheduler;

/**
 * The unit test cases for class ScreeningTool.
 * 
 * @author urtks
 * @version 1.0
 */
public class ScreeningToolTest extends BaseTestCase {

	/**
	 * Represents the path of the screener config file.
	 */
	protected static final String SCREENER_CONFIG_FILE = "screener.xml";

	/**
	 * Represents the path of the object factory config file.
	 */
	protected static final String OBJECT_FACTORY_CONFIG_FILE = "object_factory.xml";

	/**
	 * Represents the config namespace of the screener.
	 */
	protected static final String SCREENER_NAMESPACE = "com.cronos.onlinereview.autoscreening.tool.Screener";

	/**
	 * Aggregates all tests in this class.
	 * 
	 * @return test suite aggregating all tests.
	 */
	public static Test suite() {
		return new TestSuite(ScreeningToolTest.class);
	}

	/**
	 * Sets up the test environment.
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	protected void setUp() throws Exception {
		super.setUp();

		ConfigManager.getInstance().add(OBJECT_FACTORY_CONFIG_FILE);
		ConfigManager.getInstance().add(SCREENER_CONFIG_FILE);
	}

	/**
	 * Clean up the test environment.
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	protected void tearDown() throws Exception {
		Scheduler scheduler = new Scheduler(ScreeningTool.class.getName());
		scheduler.shutdown();

		super.tearDown();
	}

	/**
	 * <p>
	 * Accuracy test of the method <code>static void main(String[] args)</code>.
	 * </p>
	 * <p>
	 * usage should be printed.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testAccuracyMain1() throws Exception {

		ScreeningTool.main(new String[] { "a", "b", "c" });
	}

	/**
	 * <p>
	 * Accuracy test of the method <code>static void main(String[] args)</code>.
	 * </p>
	 * <p>
	 * screener id is -1.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testAccuracyMain2() throws Exception {

		ScreeningTool.main(new String[] { "-screenerId=-1",
				"-configNamespace=" + SCREENER_NAMESPACE, "-interval=300000" });
	}

	/**
	 * <p>
	 * Accuracy test of the method <code>static void main(String[] args)</code>.
	 * </p>
	 * <p>
	 * interval should be at least 1 second
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testAccuracyMain3() throws Exception {

		ScreeningTool.main(new String[] { "-screenerId=1",
				"-configNamespace=" + SCREENER_NAMESPACE, "-interval=300" });
	}

	/**
	 * <p>
	 * Accuracy test of the method <code>static void main(String[] args)</code>.
	 * </p>
	 * <p>
	 * namespace does not exist.
	 * </p>
	 * 
	 * @throws Exception
	 *             throw any exception to JUnit
	 */
	public void testAccuracyMain4() throws Exception {

		ScreeningTool.main(new String[] { "-screenerId=1",
				"-configNamespace=" + "do_not_exist", "-interval=3000" });
	}
}