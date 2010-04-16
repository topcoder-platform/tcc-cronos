/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.actions;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
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
     * Represents a string over 100 characters used for unit tests.
     */
    public static final String STRING_OVER_100 = "000000000000000000000000000000000000000000000000000000000000000000"
        + "00000000000000000000000000000000000";

    /**
     * Represents a string over 255 characters used for unit tests.
     */
    public static final String STRING_OVER_255 = "00000000000000000000000000000000000000000000000000000000000"
        + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"
        + "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";

    /**
     * Represents a string over 50 characters used for unit tests.
     */
    public static final String STRING_OVER_50 = "000000000000000000000000000000000000000000000000000";

    /**
     * Represents the key to use when verifying user is logged in.
     */
    public static final String KEY_FOR_LOGIN_CHECK = "USER_ID_KEY";

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
        // reset mock data
        MockContestServiceFacade.clearStudioCompetitions();
        MockContestServiceFacade.clearSoftwareCompetitions();
        MockContestServiceFacade.clearDocuments();
        MockContestServiceFacade.setFailGetCategories(false);
        MockContestServiceFacade.setFailGetTechnologies(false);
        fieldValues.clear();
    }

    /**
     * Gets a value from an instance field using reflection.
     *
     * @param instance the instance from which to fetch the value
     * @param fieldName the name of the field
     * @return the value of the field
     * @throws Exception if any error occurs getting the field value
     */
    public static Object getInstanceField(Object instance, String fieldName) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    /**
     * Sets an instance field to a value using reflection.
     *
     * @param instance the instance on which to set the field value
     * @param clazz the type of class
     * @param fieldName the name of the field
     * @param fieldValue the value of the field
     * @throws Exception if any problem occurs setting the field value
     */
    @SuppressWarnings("unchecked")
    public static void setInstanceField(Object instance, Class clazz, String fieldName,
        Object fieldValue) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, fieldValue);
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
        assertEquals("wrong action result returned", "success", proxy.execute());
        assertTrue("there should be field errors", action.hasFieldErrors());
        assertEquals("wrong number of field errors returned", 1, action.getFieldErrors().size());
        assertEquals("wrong key in field errors", key, action.getFieldErrors().keySet().toArray()[0]);
        assertEquals("wrong message in field errors", msg, action.getFieldErrors().get(key).get(0));
    }

    /**
     * Asserts that inheritance is correct for a class by checking that the class extends the given
     * superclass.
     *
     * @param clazz the class to check
     * @param expectedSuperclass the expected superclass that the class should extend
     */
    public static void assertSuperclass(Class<?> clazz, Class<?> expectedSuperclass) {
        assertTrue("The inheritance is wrong, the class doesn't extend " + expectedSuperclass.getName(), clazz
            .getSuperclass().equals(expectedSuperclass));
    }

    /**
     * Injects a session map into the action proxy.
     *
     * @param proxy the action proxy
     */
    public static void injectSessionMapIntoProxy(ActionProxy proxy) {
        // inject session map into proxy and load user id so authentication will pass
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("USER_ID_KEY", "topcoder");
        proxy.getInvocation().getInvocationContext().setSession(session);
    }

    /**
     * Gets file contests for given file specified by argument.
     *
     * @param path full path to file
     * @return file contents
     * @throws Exception if some error occurs reading file or file doesn't exist
     */
    public static String getFileContents(String path) throws Exception {
        FileReader inputStream = null;
        try {
            inputStream = new FileReader(path);
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputStream, writer);
            return writer.toString();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
