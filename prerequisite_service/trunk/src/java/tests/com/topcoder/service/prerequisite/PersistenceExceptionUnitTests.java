/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import com.topcoder.service.prerequisite.ejb.PersistenceFault;
import com.topcoder.util.errorhandling.ExceptionData;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link PersistenceException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PersistenceExceptionUnitTests extends TestCase {
    /**
     * Represents a string with a detail message.
     */
    private static final String DETAIL_MESSAGE = "detail";

    /**
     * Represents a throwable cause.
     */
    private static final Throwable CAUSE = new Exception("UnitTest");

    /**
     * <p>
     * Represents the fault info.
     * </p>
     */
    private static final PersistenceFault FAULT_INFO = new PersistenceFault();

    /**
     * <p>
     * Represents the persistence message.
     * </p>
     */
    private static final String PERSISTENCE_MESSAGE = "Duplicate record";

    /**
     * <p>
     * Represents the exception data.
     * </p>
     */
    private static final ExceptionData EXCEPTION_DATA = new ExceptionData();

    /**
     * <p>
     * Represents the application code set in exception data.
     * </p>
     */
    private static final String APPLICATION_CODE = "Accuracy";

    static {
        EXCEPTION_DATA.setApplicationCode(APPLICATION_CODE);
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(PersistenceExceptionUnitTests.class);
    }

    /**
     * <p>
     * Unit test <code>PersistenceException(String, PersistenceFault)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the fault info should be correct.
     * </p>
     */
    public void testPersistenceException_accuracy1() {
        // Construct PersistenceException with a detail message and fault info
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, FAULT_INFO);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertNull("Incorrect perssitence message.", exception.getPersistenceMessage());
    }

    /**
     * <p>
     * Unit test <code>PersistenceException(String, PersistenceFault, Throwable)</code> constructor.
     * </p>
     * <p>
     * The detail error message, fault info and the original cause of error should be correct.
     * </p>
     */
    public void testPersistenceException_accuracy2() {
        // Construct PersistenceException with a detail message, fault info and a cause
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, FAULT_INFO, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertNull("Incorrect perssitence message.", exception.getPersistenceMessage());
    }

    /**
     * <p>
     * Unit test <code>PersistenceException(String, String)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the persistence message should be correct.
     * </p>
     */
    public void testPersistenceException_accuracy3() {
        // Construct PersistenceException with a detail message
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, PERSISTENCE_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertNull("The fault info should be null.", exception.getFaultInfo());
        assertEquals("Incorrect perssitence message.", PERSISTENCE_MESSAGE, exception.getPersistenceMessage());
    }

    /**
     * <p>
     * Unit test <code>PersistenceException(String, Throwable, String)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the original cause of error and the persistence message should be correct.
     * </p>
     */
    public void testPersistenceException_accuracy4() {
        // Construct PersistenceException with a detail message, a cause and the persistence message.
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, CAUSE, PERSISTENCE_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertEquals("Incorrect perssitence message.", PERSISTENCE_MESSAGE, exception.getPersistenceMessage());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>PersistenceException(String, ExceptionData, String)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the exception data and the persistence message should be correct.
     * </p>
     */
    public void testPersistenceException_accuracy5() {
        // Construct PersistenceException with a detail message, a cause and the persistence message
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, EXCEPTION_DATA, PERSISTENCE_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
        assertEquals("Incorrect persistence message.", PERSISTENCE_MESSAGE, exception.getPersistenceMessage());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>PersistenceException(String, Throwable, ExceptionData, String)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the cause exception, the exception data and the persistence message should be correct.
     * </p>
     */
    public void testPersistenceException_accuracy6() {
        // Construct PersistenceException with a detail message, a cause, exception data and the persistence message
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, CAUSE, EXCEPTION_DATA,
                PERSISTENCE_MESSAGE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertEquals("Incorrect persistence message.", PERSISTENCE_MESSAGE, exception.getPersistenceMessage());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test for <code>PersistenceException#getPersistenceMessage()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetPersistenceMessage_accuracy() throws Exception {
        // Construct PersistenceException with a detail message and fault info
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, FAULT_INFO);
        assertNull("Incorrect perssitence message.", exception.getPersistenceMessage());

        UnitTestHelper.setFieldValue(exception, "persistenceMessage", PERSISTENCE_MESSAGE);
        assertEquals("Incorrect persistence message.", PERSISTENCE_MESSAGE, exception.getPersistenceMessage());
    }

    /**
     * <p>
     * Unit test for <code>PersistenceException#getFaultInfo()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFaultInfo_accuracy() throws Exception {
        // Construct PersistenceException with a detail message
        PersistenceException exception = new PersistenceException(DETAIL_MESSAGE, "Error");
        assertNull("The fault info should be null.", exception.getFaultInfo());

        UnitTestHelper.setFieldValue(exception, "faultInfo", FAULT_INFO);
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
    }
}
