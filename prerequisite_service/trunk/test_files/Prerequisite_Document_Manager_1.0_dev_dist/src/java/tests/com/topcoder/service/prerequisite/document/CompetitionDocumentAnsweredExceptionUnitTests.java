/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.prerequisite.document.model.MemberAnswer;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Unit test for <code>{@link CompetitionDocumentAnsweredException}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompetitionDocumentAnsweredExceptionUnitTests extends TestCase {

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
     * Represents the MemberAnswer instance.
     * </p>
     */
    private static final MemberAnswer MEMBER_ANSWER = new MemberAnswer();

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CompetitionDocumentAnsweredExceptionUnitTests.class);
    }

    /**
     * Tests accuracy of <code>CompetitionDocumentAnsweredException(String, MemberAnswer)</code> constructor. The
     * detail error message should be correct.
     */
    public void testCompetitionDocumentAnsweredExceptionStringAccuracy() {
        // Construct CompetitionDocumentAnsweredException with a detail message
        CompetitionDocumentAnsweredException exception = new CompetitionDocumentAnsweredException(DETAIL_MESSAGE,
                MEMBER_ANSWER);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message should be identical.", DETAIL_MESSAGE, exception.getMessage());

        // verify that the MemberAnswer instance is properly set.
        assertSame("The MemberAnswer instance is not set properly.", MEMBER_ANSWER, exception.getMemberAnswer());
    }

    /**
     * Tests accuracy of <code>CompetitionDocumentAnsweredException(String, ExceptionData, MemberAnswer)</code>
     * constructor. The detail error message and the exception data should be correct.
     */
    public void testCompetitionDocumentAnsweredExceptionStringExceptionDataAccuracy() {
        // Construct CompetitionDocumentAnsweredException with a detail message and a cause
        CompetitionDocumentAnsweredException exception = new CompetitionDocumentAnsweredException(DETAIL_MESSAGE,
                EXCEPTION_DATA, MEMBER_ANSWER);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // verify that the MemberAnswer instance is properly set.
        assertSame("The MemberAnswer instance is not set properly.", MEMBER_ANSWER, exception.getMemberAnswer());
    }

    /**
     * Tests accuracy of <code>CompetitionDocumentAnsweredException(String, Throwable, MemberAnswer)</code>
     * constructor. The detail error message and the original cause of error should be correct.
     */
    public void testCompetitionDocumentAnsweredExceptionStringThrowableAccuracy() {
        // Construct CompetitionDocumentAnsweredException with a detail message and a cause
        CompetitionDocumentAnsweredException exception = new CompetitionDocumentAnsweredException(DETAIL_MESSAGE,
                CAUSE, MEMBER_ANSWER);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());

        // verify that the MemberAnswer instance is properly set.
        assertSame("The MemberAnswer instance is not set properly.", MEMBER_ANSWER, exception.getMemberAnswer());
    }

    /**
     * Tests accuracy of
     * <code>CompetitionDocumentAnsweredException(String, Throwable, ExceptionData, MemberAnswer)</code> constructor.
     * The detail error message, the cause exception and the exception data should be correct.
     */
    public void testCompetitionDocumentAnsweredExceptionStringThrowableExceptionDataAccuracy() {
        // Construct CompetitionDocumentAnsweredException with a detail message and a cause
        CompetitionDocumentAnsweredException exception = new CompetitionDocumentAnsweredException(DETAIL_MESSAGE,
                CAUSE, EXCEPTION_DATA, MEMBER_ANSWER);

        // Verify that there is a detail message
        assertNotNull("Should have message.", exception.getMessage());
        assertEquals("Detailed error message with cause should be correct.", DETAIL_MESSAGE, exception.getMessage());

        // Verify that the exception data is correctly set.
        assertNotNull("application code should not null", exception.getApplicationCode());
        assertEquals("exception data is not set.", APPLICATION_CODE, exception.getApplicationCode());

        // Verify that there is a cause
        assertNotNull("Should have cause.", exception.getCause());
        assertSame("Cause should be identical.", CAUSE, exception.getCause());

        // verify that the MemberAnswer instance is properly set.
        assertSame("The MemberAnswer instance is not set properly.", MEMBER_ANSWER, exception.getMemberAnswer());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocumentAnsweredException#getMemberAnswer()</code> method.
     * </p>
     * <p>
     * It should return the memberAnswer instance set in constructor.
     * </p>
     */
    public void testGetMemberAnswerAccuracy1() {
        CompetitionDocumentAnsweredException exception = new CompetitionDocumentAnsweredException(DETAIL_MESSAGE,
                MEMBER_ANSWER);
        // verify that the MemberAnswer instance is properly set.
        assertSame("The MemberAnswer instance is not set properly.", MEMBER_ANSWER, exception.getMemberAnswer());
    }

    /**
     * <p>
     * Unit test for <code>CompetitionDocumentAnsweredException#getMemberAnswer()</code> method.
     * </p>
     * <p>
     * It should return the memberAnswer instance set in constructor.
     * </p>
     */
    public void testGetMemberAnswerAccuracy2() {
        CompetitionDocumentAnsweredException exception = new CompetitionDocumentAnsweredException(DETAIL_MESSAGE, null);
        // verify that the MemberAnswer instance is properly set.
        assertNull("The MemberAnswer instance is not set properly.", exception.getMemberAnswer());
    }
}
