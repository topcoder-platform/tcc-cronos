/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.registration.RegistrationPersistenceException;


/**
 * <p>
 * Unit test for <code>{@link WebServiceWrapperForResourceManagementException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class WebServiceWrapperForResourceManagementExceptionUnitTests extends BaseTestCase {

    /**
     * <p>
     * <code>WebServiceWrapperForResourceManagementException</code> should be subclass of
     * <code>RegistrationPersistenceException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "WebServiceWrapperForResourceManagementException should be subclass of RegistrationPersistenceException",
            WebServiceWrapperForResourceManagementException.class.getSuperclass()
            == RegistrationPersistenceException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>WebServiceWrapperForResourceManagementException(String)</code> constructor.
     * The detail error message should be properly set.
     * </p>
     */
    public void testCtor1() {
        // Construct WebServiceWrapperForResourceManagementException with a detail message
        WebServiceWrapperForResourceManagementException exception =
            new WebServiceWrapperForResourceManagementException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>WebServiceWrapperForResourceManagementException(String, Throwable)</code> constructor.
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    public void testCtor2() {
        // Construct WebServiceWrapperForResourceManagementException with a detail message and a cause
        WebServiceWrapperForResourceManagementException exception =
            new WebServiceWrapperForResourceManagementException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }

    /**
     * <p>
     * Tests accuracy of <code>WebServiceWrapperForResourceManagementException(String, ExceptionData)
     * </code> constructor.
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    public void testCtor3() {
        // Construct WebServiceWrapperForResourceManagementException with a detail message and a cause
        WebServiceWrapperForResourceManagementException exception =
            new WebServiceWrapperForResourceManagementException(DETAIL_MESSAGE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
    }

    /**
     * <p>
     * Tests accuracy of <code>WebServiceWrapperForResourceManagementException(String, Throwable, ExceptionData)
     * </code> constructor.
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    public void testCtor4() {
        // Construct WebServiceWrapperForResourceManagementException with a detail message and a cause
        WebServiceWrapperForResourceManagementException exception =
            new WebServiceWrapperForResourceManagementException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
    }
}
