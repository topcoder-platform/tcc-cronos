/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.action.contest.launch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.ActionProxy;

/**
 * Test Helper for unit tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper extends StrutsSpringTestCase {

    /**
     * Represents the type of testing mode (demo or unit test).
     */
    private static String testingMode;

    /**
     * Represents the map of field values used for unit tests. The key is the field name and the value is the
     * value for the field.
     */
    private static Map<String, Object> fieldValues = new HashMap<String, Object>();

    /**
     * Private constructor to prevent class instantiation.
     */
    private TestHelper() {
    }

    /**
     * Gets the map of field values used for unit tests.
     *
     * @return the map of field values used for unit tests.
     */
    public static Map<String, Object> getFieldValues() {
        return fieldValues;
    }

    /**
     * The getter for the type of testing mode (demo or unit test).
     *
     * @return the type of testing mode (demo or unit test)
     */
    public static String getTestingMode() {
        if (testingMode == null) {
            Properties properties = new Properties();
            try {
                properties.load(TestHelper.class.getResourceAsStream("/test.properties"));
            } catch (IOException e) {
                throw new RuntimeException("could not load test.properties", e);
            }

            // get the testing mode (demo or unit test)
            testingMode = properties.getProperty("TESTING_MODE");
        }
        return testingMode;
    }

    /**
     * Cleans up environment before and after unit tests.
     */
    public static void cleanupEnvironment() {
        fieldValues.clear();
        MockDirectServiceFacade.clearMockData();
        MockContestServiceFacade.clearMockData();
    }

    /**
     * Sets up environment before unit tests.
     */
    public static void setupEnvironment() {
        cleanupEnvironment();
        MockDirectServiceFacade.initMockData();
        MockContestServiceFacade.initMockData();
    }

    /**
     * Validates the struts field validation for accuracy.
     *
     * @param proxy the struts 2 action proxy to use when running the action
     * @param action the action to run
     * @param key the field key that should be associated with the field error message
     * @param msg the expected error message
     *
     * @throws Exception if some unknown error occurred
     */
    public static void assertInvalidField(ActionProxy proxy, AbstractAction action, String key, String msg)
        throws Exception {
        proxy.execute();
        assertTrue("there should be field errors", action.hasFieldErrors());
        assertEquals("wrong number of field errors returned", 1, action.getFieldErrors().size());
        assertEquals("wrong key in field errors", key, action.getFieldErrors().keySet().toArray()[0]);
        assertEquals("wrong message in field errors", msg, action.getFieldErrors().get(key).get(0));
    }
}
