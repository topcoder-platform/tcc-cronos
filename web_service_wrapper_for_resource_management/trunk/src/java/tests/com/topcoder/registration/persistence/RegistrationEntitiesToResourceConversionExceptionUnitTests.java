/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

/**
 * <p>
 * Unit test for <code>{@link RegistrationEntitiesToResourceConversionException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegistrationEntitiesToResourceConversionExceptionUnitTests extends BaseTestCase {

    /**
     * <p>
     * <code>RegistrationEntitiesToResourceConversionException</code> should be subclass of
     * <code>WebServiceWrapperForResourceManagementException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "RegistrationEntitiesToResourceConversionException should be subclass"
            + " of WebServiceWrapperForResourceManagementException",
            RegistrationEntitiesToResourceConversionException.class.getSuperclass()
            == WebServiceWrapperForResourceManagementException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>RegistrationEntitiesToResourceConversionException(String)</code> constructor.
     * The detail error message should be properly set.
     * </p>
     */
    public void testCtor1() {
        // Construct RegistrationEntitiesToResourceConversionException with a detail message
        RegistrationEntitiesToResourceConversionException exception =
            new RegistrationEntitiesToResourceConversionException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
    }

    /**
     * <p>
     * Tests accuracy of <code>RegistrationEntitiesToResourceConversionException(String, Throwable)</code> constructor.
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    public void testCtor2() {
        // Construct RegistrationEntitiesToResourceConversionException with a detail message and a cause
        RegistrationEntitiesToResourceConversionException exception =
            new RegistrationEntitiesToResourceConversionException(DETAIL_MESSAGE, CAUSE);

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
     * Tests accuracy of <code>RegistrationEntitiesToResourceConversionException(String, ExceptionData)
     * </code> constructor.
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    public void testCtor3() {
        // Construct RegistrationEntitiesToResourceConversionException with a detail message and a cause
        RegistrationEntitiesToResourceConversionException exception =
            new RegistrationEntitiesToResourceConversionException(DETAIL_MESSAGE, EXCEPTION_DATA);

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
     * Tests accuracy of <code>RegistrationEntitiesToResourceConversionException(String, Throwable, ExceptionData)
     * </code> constructor.
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    public void testCtor4() {
        // Construct RegistrationEntitiesToResourceConversionException with a detail message and a cause
        RegistrationEntitiesToResourceConversionException exception =
            new RegistrationEntitiesToResourceConversionException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

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
