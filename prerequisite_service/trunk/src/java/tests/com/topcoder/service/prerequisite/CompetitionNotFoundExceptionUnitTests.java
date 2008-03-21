/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.ejb.CompetitionNotFoundFault;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test for <code>{@link CompetitionNotFoundException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionNotFoundExceptionUnitTests extends TestCase {

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
    private static final CompetitionNotFoundFault FAULT_INFO = new CompetitionNotFoundFault();

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
        return new TestSuite(CompetitionNotFoundExceptionUnitTests.class);
    }

    /**
     * <p>
     * Unit test <code>CompetitionNotFoundException(String, CompetitionNotFoundFault)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the fault info should be correct.
     * </p>
     */
    public void testCompetitionNotFoundException_accuracy1() {
        // Construct CompetitionNotFoundException with a detail message and fault info
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, FAULT_INFO);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertEquals("Incorrect competition id.", -1, exception.getCompetitionIdNotFound());
    }

    /**
     * <p>
     * Unit test <code>CompetitionNotFoundException(String, CompetitionNotFoundFault, Throwable)</code> constructor.
     * </p>
     * <p>
     * The detail error message, fault info and the original cause of error should be correct.
     * </p>
     */
    public void testCompetitionNotFoundException_accuracy2() {
        // Construct CompetitionNotFoundException with a detail message, fault info and a cause
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, FAULT_INFO, CAUSE);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
        assertEquals("Incorrect competition id.", -1, exception.getCompetitionIdNotFound());
    }

    /**
     * <p>
     * Unit test <code>CompetitionNotFoundException(String, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message and the not found competition id should be correct.
     * </p>
     */
    public void testCompetitionNotFoundException_accuracy3() {
        // Construct CompetitionNotFoundException with a detail message
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());
        assertEquals("Incorrect competition id.", 1, exception.getCompetitionIdNotFound());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>CompetitionNotFoundException(String, Throwable, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the original cause of error and not found competition id should be correct.
     * </p>
     */
    public void testCompetitionNotFoundException_accuracy4() {
        // Construct CompetitionNotFoundException with a detail message, a cause and not found competition id.
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, CAUSE, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertEquals("Incorrect competition id.", 1, exception.getCompetitionIdNotFound());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>CompetitionNotFoundException(String, ExceptionData, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the exception data and not found competition id should be correct.
     * </p>
     */
    public void testCompetitionNotFoundException_accuracy5() {
        // Construct CompetitionNotFoundException with a detail message, a cause and not found competition id
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, EXCEPTION_DATA, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());
        assertEquals("Incorrect competition id.", 1, exception.getCompetitionIdNotFound());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test <code>CompetitionNotFoundException(String, Throwable, ExceptionData, long)</code> constructor.
     * </p>
     * <p>
     * The detail error message, the cause exception, the exception data and not found competition id should be correct.
     * </p>
     */
    public void testCompetitionNotFoundException_accuracy6() {
        // Construct CompetitionNotFoundException with a detail message, a cause, exception data and not found
        // competition id
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, CAUSE,
                EXCEPTION_DATA, 1);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());
        assertEquals("Incorrect competition id.", 1, exception.getCompetitionIdNotFound());
        assertNull("The fault info should be null.", exception.getFaultInfo());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionNotFoundException#getCompetitionIdNotFound()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetCompetitionIdNotFound_accuracy() throws Exception {
        // Construct CompetitionNotFoundException with a detail message and fault info
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, FAULT_INFO);
        assertEquals("Incorrect competition id.", -1, exception.getCompetitionIdNotFound());

        UnitTestHelper.setFieldValue(exception, "competitionIdNotFound", 1L);
        assertEquals("Incorrect competition id.", 1, exception.getCompetitionIdNotFound());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionNotFoundException#getFaultInfo()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testGetFaultInfo_accuracy() throws Exception {
        // Construct CompetitionNotFoundException with a detail message
        CompetitionNotFoundException exception = new CompetitionNotFoundException(DETAIL_MESSAGE, 1);
        assertNull("The fault info should be null.", exception.getFaultInfo());

        UnitTestHelper.setFieldValue(exception, "faultInfo", FAULT_INFO);
        assertSame("The fault info is not set.", FAULT_INFO, exception.getFaultInfo());
    }
}
