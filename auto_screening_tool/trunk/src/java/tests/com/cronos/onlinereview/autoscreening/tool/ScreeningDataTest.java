/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The unit test cases for class ScreeningData.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ScreeningDataTest extends TestCase {

    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreeningDataTest.class);
    }

    /**
     * <p>
     * Accuracy Test for the setFileIdentifier.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetFileIdentifier1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setFileIdentifier("file1");
        assertEquals("check error", "file1", screeningData.getFileIdentifier());
    }

    /**
     * <p>
     * Failure Test for the setFileIdentifier.
     * </p>
     * <p>
     * fileIdentifier is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetFileIdentifier1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setFileIdentifier(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "fileIdentifier should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setFileIdentifier.
     * </p>
     * <p>
     * fileIdentifier is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetFileIdentifier2() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setFileIdentifier("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "fileIdentifier should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setUploadId.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetUploadId1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setUploadId(1);
        assertEquals("check error", 1, screeningData.getUploadId());
    }

    /**
     * <p>
     * Failure Test for the setUploadId.
     * </p>
     * <p>
     * uploadId is Long.MIN_VALUE. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetUploadId1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setUploadId(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "uploadId should be > 0.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setProjectId.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetProjectId1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setProjectId(1);
        assertEquals("check error", 1, screeningData.getProjectId());
    }

    /**
     * <p>
     * Failure Test for the setProjectId.
     * </p>
     * <p>
     * projectId is Long.MIN_VALUE. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetProjectId1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setProjectId(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "projectId should be > 0.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setProjectCategoryId.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetProjectCategoryId1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setProjectCategoryId(1);
        assertEquals("check error", 1, screeningData.getProjectCategoryId());
    }

    /**
     * <p>
     * Failure Test for the setProjectCategoryId.
     * </p>
     * <p>
     * projectCategoryId is Long.MIN_VALUE. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetProjectCategoryId1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setProjectCategoryId(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "projectCategoryId should be > 0.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setSubmitterHandle.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetSubmitterHandle1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setSubmitterHandle("handle1");
        assertEquals("check error", "handle1", screeningData.getSubmitterHandle());
    }

    /**
     * <p>
     * Failure Test for the setSubmitterHandle.
     * </p>
     * <p>
     * submitterHandle is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterHandle1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterHandle(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterHandle should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setSubmitterHandle.
     * </p>
     * <p>
     * submitterHandle is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailuresetSubmitterHandle2() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterHandle("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterHandle should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setSubmitterEmail.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetSubmitterEmail1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setSubmitterEmail("email1");
        assertEquals("check error", "email1", screeningData.getSubmitterEmail());
    }

    /**
     * <p>
     * Failure Test for the setSubmitterEmail.
     * </p>
     * <p>
     * submitterEmail is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterEmail1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterEmail(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterEmail should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setSubmitterEmail.
     * </p>
     * <p>
     * submitterEmail is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailuresetSubmitterEmail2() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterEmail("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterEmail should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setSubmitterFirstName.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetSubmitterFirstName1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setSubmitterFirstName("firstName1");
        assertEquals("check error", "firstName1", screeningData.getSubmitterFirstName());
    }

    /**
     * <p>
     * Failure Test for the setSubmitterFirstName.
     * </p>
     * <p>
     * submitterFirstName is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterFirstName1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterFirstName(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterFirstName should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setSubmitterFirstName.
     * </p>
     * <p>
     * submitterFirstName is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailuresetSubmitterFirstName2() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterFirstName("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterFirstName should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setSubmitterLastName.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetSubmitterLastName1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setSubmitterLastName("lastName1");
        assertEquals("check error", "lastName1", screeningData.getSubmitterLastName());
    }

    /**
     * <p>
     * Failure Test for the setSubmitterLastName.
     * </p>
     * <p>
     * submitterLastName is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterLastName1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterLastName(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterLastName should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setSubmitterLastName.
     * </p>
     * <p>
     * submitterLastName is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterLastName2() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterLastName("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterLastName should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setSubmitterAlternativeEmails.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracysetSubmitterAlternativeEmails1() throws Exception {
        ScreeningData screeningData = new ScreeningData();
        screeningData.setSubmitterAlternativeEmails(new String[] {"email1", "email2"});
        assertEquals("check error", "email1", screeningData.getSubmitterAlternativeEmails()[0]);
        assertEquals("check error", "email2", screeningData.getSubmitterAlternativeEmails()[1]);
    }

    /**
     * <p>
     * Failure Test for the setSubmitterAlternativeEmails.
     * </p>
     * <p>
     * alternativeEmails is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterAlternativeEmails1() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterAlternativeEmails(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterAlternativeEmails should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setSubmitterAlternativeEmails.
     * </p>
     * <p>
     * alternativeEmails contains null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterAlternativeEmails2() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterAlternativeEmails(new String[] {"email1", null});
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "submitterAlternativeEmails[1] should not be null.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setSubmitterAlternativeEmails.
     * </p>
     * <p>
     * alternativeEmails contains empty string. IllegalArgumentException is
     * expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetSubmitterAlternativeEmails3() throws Exception {
        ScreeningData screeningData = new ScreeningData();

        try {
            screeningData.setSubmitterAlternativeEmails(new String[] {"email1", "   "});
        } catch (IllegalArgumentException e) {
            assertEquals("check message",
                "submitterAlternativeEmails[1] should not be empty (trimmed).", e.getMessage());
        }
    }
}