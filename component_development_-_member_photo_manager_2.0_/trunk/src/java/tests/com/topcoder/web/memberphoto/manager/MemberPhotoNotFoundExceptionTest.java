/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.memberphoto.manager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.errorhandling.ExceptionData;

/**
 * <p>
 * Tests the functionality of {@link MemberPhotoNotFoundException} class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class MemberPhotoNotFoundExceptionTest extends TestCase {

    /**
     * Error message used for testing.
     */
    private static final String ERROR_MSG = "Unit test error";

    /**
     * The exception data used for testing.
     */
    private ExceptionData data;

    /**
     * The inner exception used.
     */
    private Exception exception;

    /**
     * Represents the member id.
     */
    private long memberId;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    @Override
    protected void setUp() throws Exception {
        exception = new Exception();
        data = new ExceptionData();
        data.setInformation("KEY", "VALUE");
        memberId = 10;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MemberPhotoNotFoundExceptionTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoNotFoundException#MemberPhotoNotFoundException(String, long)} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoNotFoundException() {
        MemberPhotoNotFoundException memberPhotoNotFoundException = new MemberPhotoNotFoundException(ERROR_MSG,
                memberId);
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException);
        assertEquals("MemberPhotoNotFoundException creation failed", ERROR_MSG, memberPhotoNotFoundException
                .getMessage());
        assertEquals("MemberPhotoNotFoundException creation failed", memberId, memberPhotoNotFoundException
                .getMemberId());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoNotFoundException#MemberPhotoNotFoundException(String, Throwable, long)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoNotFoundException1() {
        MemberPhotoNotFoundException memberPhotoNotFoundException = new MemberPhotoNotFoundException(ERROR_MSG,
                exception, memberId);
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException);
        assertEquals("MemberPhotoNotFoundException creation failed", ERROR_MSG, memberPhotoNotFoundException
                .getMessage());
        assertEquals("MemberPhotoNotFoundException creation failed", memberId, memberPhotoNotFoundException
                .getMemberId());
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException.getCause());
    }

    /**
     * <p>
     * Accuracy test for {@link MemberPhotoNotFoundException#MemberPhotoNotFoundException(String, ExceptionData, long)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoNotFoundException2() {
        MemberPhotoNotFoundException memberPhotoNotFoundException = new MemberPhotoNotFoundException(ERROR_MSG,
                data, memberId);
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException);
        assertEquals("MemberPhotoNotFoundException creation failed", ERROR_MSG, memberPhotoNotFoundException
                .getMessage());
        assertEquals("MemberPhotoNotFoundException creation failed", memberId, memberPhotoNotFoundException
                .getMemberId());
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException
                .getInformation("KEY"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link MemberPhotoNotFoundException#MemberPhotoNotFoundException(String, Throwable, ExceptionData, long)}
     * constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     *
     */
    public void test_accuracy_MemberPhotoNotFoundException3() {
        MemberPhotoNotFoundException memberPhotoNotFoundException = new MemberPhotoNotFoundException(ERROR_MSG,
                exception, data, memberId);
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException);
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException.getCause());
        assertEquals("MemberPhotoNotFoundException creation failed", ERROR_MSG, memberPhotoNotFoundException
                .getMessage());
        assertEquals("MemberPhotoNotFoundException creation failed", memberId, memberPhotoNotFoundException
                .getMemberId());
        assertNotNull("MemberPhotoNotFoundException creation failed", memberPhotoNotFoundException
                .getInformation("KEY"));
    }
}
