/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.apps.screening.applications.specification.stresstests;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.apps.screening.applications.specification.ValidationManager;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This class contains stress tests for this component.
 * </p>
 * 
 * @author nhzp339
 * @version 1.0
 */
public class StressTests extends TestCase {
	/**
	 * <p>
	 * The loop times.
	 * </p>
	 */
	private static final int LOOP_TIMES = 10;
		
	/**
	 * <p>
	 * The default namespace for XMI handler.
	 * </p>
	 *  
	 */
	public static final String XMIHANDLER_NAMESPACE = "com.topcoder.util.xmi.parser.default.handler";

	/**
	 * <p>
	 * ConfigManager instance.
	 * </p>
	 */
	private static final ConfigManager manager = ConfigManager.getInstance();

	/**
	 * <p>
	 * XMI file name.
	 * </p>
	 */
	private String xmiFileName = "test_files/stresstests/XMI_Parser.xmi";

	/**
	 * <p>
	 * The stress test with default XML formatter.
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test failed.
	 */
	public void testStress1() throws Exception {
		for (Iterator it = manager.getAllNamespaces(); it.hasNext();) {
			manager.removeNamespace((String) it.next());
		}
		manager.add(XMIHANDLER_NAMESPACE, "stresstests/stress_xmi_handler.xml",
				ConfigManager.CONFIG_XML_FORMAT);
		manager.add("stresstests/stress.xml");
		for (int i = 0; i < LOOP_TIMES; ++i) {
			assertNotNull(this.screening(), "The result should be got.");
		}
	}

	/**
	 * <p>
	 * The stress test with customer formatter.
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test failed.
	 */
	public void testStress2() throws Exception {
		for (Iterator it = manager.getAllNamespaces(); it.hasNext();) {
			manager.removeNamespace((String) it.next());
		}
		manager.add(XMIHANDLER_NAMESPACE, "stresstests/stress_xmi_handler.xml",
				ConfigManager.CONFIG_XML_FORMAT);
		manager.add("stresstests/stress_custom_formatter.xml");
		for (int i = 0; i < LOOP_TIMES; ++i) {
			assertNotNull(this.screening(), "The result should be got.");
		}
	}

	/**
	 * <p>
	 * Validate XMI file.
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test failed.
	 * 
	 * @return The screening result.
	 */
	private String screening() throws Exception {
		// Create an instance of the ValidationManager.
		ValidationManager validationManager = new ValidationManager();
		// Perform the validation.
		String[] output = validationManager.validate(new File(xmiFileName));
		// Return the output data.
		return Arrays.asList(output).toString();
	}
}