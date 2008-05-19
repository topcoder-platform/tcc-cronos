/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

/**
 * <p>
 * Unit test for <code>{@link ResourceManagementException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceManagementExceptionUnitTests extends BaseTestCase {

    /**
     * <p>
     * <code>ResourceManagementException</code> should be subclass of
     * <code>WebServiceWrapperForResourceManagementException</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
            "ResourceManagementException should be subclass of WebServiceWrapperForResourceManagementException",
            ResourceManagementException.class.getSuperclass()
            == WebServiceWrapperForResourceManagementException.class);
    }

    /**
     * <p>
     * Tests accuracy of <code>ResourceManagementException(String)</code> constructor.
     * The detail error message should be properly set.
     * </p>
     */
    public void testCtor1() {
        // Construct ResourceManagementException with a detail message
        ResourceManagementException exception =
            new ResourceManagementException(DETAIL_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());

        assertNotNull("Fault bean should be created", exception.getFaultInfo());
    }

    /**
     * <p>
     * Tests accuracy of <code>ResourceManagementException(String, Throwable)</code> constructor.
     * The detail error message and the original cause of error should be properly set.
     * </p>
     */
    public void testCtor2() {
        // Construct ResourceManagementException with a detail message and a cause
        ResourceManagementException exception =
            new ResourceManagementException(DETAIL_MESSAGE, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());

        assertNotNull("Fault bean should be created", exception.getFaultInfo());
    }

    /**
     * <p>
     * Tests accuracy of <code>ResourceManagementException(String, ExceptionData)
     * </code> constructor.
     * The detail error message and the exception data should be properly set.
     * </p>
     */
    public void testCtor3() {
        // Construct ResourceManagementException with a detail message and a cause
        ResourceManagementException exception =
            new ResourceManagementException(DETAIL_MESSAGE, EXCEPTION_DATA);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be properly set.",
            DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        assertNotNull("Fault bean should be created", exception.getFaultInfo());
    }

    /**
     * <p>
     * Tests accuracy of <code>ResourceManagementException(String, Throwable, ExceptionData)
     * </code> constructor.
     * The detail error message, the cause exception and the exception data should be properly set.
     * </p>
     */
    public void testCtor4() {
        // Construct ResourceManagementException with a detail message and a cause
        ResourceManagementException exception =
            new ResourceManagementException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA);

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

        assertNotNull("Fault bean should be created", exception.getFaultInfo());
    }

    /**
     * <p>
     * Tests accuracy of <code>ResourceManagementException(String, ResourceManagementFault)</code> constructor.
     * The detail error message and fault bean should be properly set.
     * </p>
     */
    public void testCtor5() {
        ResourceManagementFault fault = new ResourceManagementFault();
        // Construct ResourceManagementException with a detail message
        ResourceManagementException exception =
            new ResourceManagementException(DETAIL_MESSAGE, fault);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());

        assertNotNull("Fault bean should be created", exception.getFaultInfo());

        assertEquals("Fault bean should be set", fault, exception.getFaultInfo());
    }
}
