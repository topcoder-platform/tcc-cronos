/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.actions;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.struts2.StrutsSpringTestCase;

import com.topcoder.security.TCSubject;
import com.topcoder.service.actions.ValidationErrorRecord;

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
     * Private constructor to prevent class instantiation.
     */
    private TestHelper() {
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
        MockContestServiceFacade.clearMockData();
        MockSpecificationReviewService.clearMockData();
        MockSpecReviewCommentService.clearMockData();
    }

    /**
     * Sets up environment before unit tests.
     *
     * @throws Exception to JUnit
     */
    public static void setupEnvironment() throws Exception {
        cleanupEnvironment();
        MockContestServiceFacade.initMockData();
        MockSpecificationReviewService.initMockData();
        MockSpecReviewCommentService.initMockData();
    }

    /**
     * Handles unexpected exception that can occur.
     *
     * @param e unexpected exception
     */
    private static void handleUnexpectedException(Exception e) {
        e.printStackTrace();
        fail("An unexpected exception occurred: " + e.toString());
        throw new RuntimeException(e);
    }

    /**
     * Gets a value from an instance field using reflection.
     *
     * @param clazz the class containing the field
     * @param instance the instance from which to fetch the value
     * @param fieldName the name of the field
     * @return the value of the field
     */
    private static Object getInstanceField(Class<?> clazz, Object instance, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (SecurityException e) {
            handleUnexpectedException(e);
        } catch (NoSuchFieldException e) {
            handleUnexpectedException(e);
        } catch (IllegalArgumentException e) {
            handleUnexpectedException(e);
        } catch (IllegalAccessException e) {
            handleUnexpectedException(e);
        }
        return null;
    }

    /**
     * Asserts that the field value in the object is the same object as the expected object.
     *
     * @param clazz the class containing the field
     * @param expected the expected value of the field
     * @param fieldName name of the field
     * @param object the object that contains the field
     */
    public static void assertFieldSame(Class<?> clazz, Object expected, Object object, String fieldName) {
        assertSame("Value of the field '" + fieldName + "' is wrong.", expected, getInstanceField(clazz, object,
            fieldName));
    }

    /**
     * Asserts that the field value equals the expected value.
     *
     * @param clazz the class containing the field
     * @param expected the expected value of the field
     * @param fieldName name of the field
     * @param object the object that contains the field
     */
    public static void assertFieldEquals(Class<?> clazz, Object expected, Object object, String fieldName) {
        assertEquals("Value of the field '" + fieldName + "' is wrong.", expected,
            getInstanceField(clazz, object, fieldName));
    }

    /**
     * Prepares the session map for the action so it can be used for unit testing.
     *
     * @param action the action to prepare
     */
    public static void prepareSessionMap(SessionAwareAction action) {
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("tcSubject", new TCSubject(1));
        action.setSession(session);
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
     * Asserts that the validation error record has the correct values.
     *
     * @param validationErrorRecord the validation error record to test
     * @param expectedPropertyName the expected property name for the validation error record
     * @param expectedMessages the expected messages for the validation error record
     */
    public static void assertValidationErrorRecord(ValidationErrorRecord validationErrorRecord,
        String expectedPropertyName, String[] expectedMessages) {
        assertEquals("the property name is wrong for the error", expectedPropertyName, validationErrorRecord
            .getPropertyName());
        assertNotNull("the messages array for the error should not be null", validationErrorRecord.getMessages());
        assertEquals("the messages array for the error contains wrong number of elements", expectedMessages.length,
            validationErrorRecord.getMessages().length);

        int ndx = 0;
        for (String expectedMessage : expectedMessages) {
            assertEquals("the validation message is wrong", expectedMessage, validationErrorRecord.getMessages()[ndx]);
            ++ndx;
        }
    }

    /**
     * Asserts that the expected exception is stored in the model.
     *
     * @param action the action which contains the model
     * @param expectedExceptionType the expected exception type
     */
    public static void assertModelException(SpecificationReviewAction action, Class<?> expectedExceptionType) {
        // make sure exception was stored in model and check that it is proper type
        Object ex = action.getModel().getData(
            SpecificationReviewAction.DEFAULT_EXCEPTION_KEY);
        assertNotNull("exception should be present in model", ex);
        assertEquals("exception is wrong type", expectedExceptionType, ex.getClass());
    }
}
