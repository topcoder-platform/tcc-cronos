/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * The unit test cases for class ScreeningTask.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ScreeningTaskTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScreeningTaskTest.class);
    }

    /**
     * <p>
     * Accuracy Test for the setScreeningStatus.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetScreeningStatus1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setScreeningStatus(ScreeningStatus.FAILED);
        assertEquals("check status", ScreeningStatus.FAILED, screeningTask.getScreeningStatus());
    }

    /**
     * <p>
     * Failure Test for the setScreeningStatus.
     * </p>
     * <p>
     * screeningStatus is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetScreeningStatus1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setScreeningStatus(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "screeningStatus should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setScreeningData.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetScreeningData1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        ScreeningData screeningData = new ScreeningData();
        screeningTask.setScreeningData(screeningData);
        assertEquals("check data", screeningData, screeningTask.getScreeningData());
    }

    /**
     * <p>
     * Failure Test for the setScreeningData.
     * </p>
     * <p>
     * screeningData is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetScreeningData1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setScreeningData(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "screeningData should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setStartTimestamp.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetStartTimestamp1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        Date now = new Date();
        screeningTask.setStartTimestamp(now);
        assertEquals("check start timestamp", now, screeningTask.getStartTimestamp());
    }

    /**
     * <p>
     * Accuracy Test for the setStartTimestamp.
     * </p>
     * <p>
     * startTimestamp can be null.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetStartTimestamp2() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setStartTimestamp(null);
        assertEquals("check start timestamp", null, screeningTask.getStartTimestamp());
    }

    /**
     * <p>
     * Accuracy Test for the setId.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetId1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setId(1);
        assertEquals("check id", 1, screeningTask.getId());
    }

    /**
     * <p>
     * Failure Test for the setId.
     * </p>
     * <p>
     * id is Long.MIN_VALUE. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetId1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setId(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "id should be > 0.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setScreenerId.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetScreenerId1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setScreenerId(1);
        assertEquals("check screener id", 1, screeningTask.getScreenerId());
    }

    /**
     * <p>
     * Accuracy Test for the setScreenerId.
     * </p>
     * <p>
     * screenerId can be Long.MIN_VALUE.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetScreenerId2() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setScreenerId(Long.MIN_VALUE);
        assertEquals("check screener id", Long.MIN_VALUE, screeningTask.getScreenerId());
    }

    /**
     * <p>
     * Failure Test for the setScreenerId.
     * </p>
     * <p>
     * screenerId is -1. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetScreenerId1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setScreenerId(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "screenerId should be > 0 or Long.MIN_VALUE.", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setCreationUser.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetCreationUser1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setCreationUser("user1");
        assertEquals("check creation user", "user1", screeningTask.getCreationUser());
    }

    /**
     * <p>
     * Failure Test for the setCreationUser.
     * </p>
     * <p>
     * submitterHandle is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetCreationUser1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setCreationUser(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "creationUser should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setCreationUser.
     * </p>
     * <p>
     * submitterHandle is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailuresetCreationUser2() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setCreationUser("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "creationUser should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setModificationUser.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetModificationUser1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        screeningTask.setModificationUser("user1");
        assertEquals("check modification user", "user1", screeningTask.getModificationUser());
    }

    /**
     * <p>
     * Failure Test for the setModificationUser.
     * </p>
     * <p>
     * submitterHandle is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetModificationUser1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setModificationUser(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "modificationUser should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Failure Test for the setModificationUser.
     * </p>
     * <p>
     * submitterHandle is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailuresetModificationUser2() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setModificationUser("   ");
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "modificationUser should not be empty (trimmed).", e
                .getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setCreationDate.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetCreationDate1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        Date now = new Date();
        screeningTask.setCreationDate(now);
        assertEquals("check creation date", now, screeningTask.getCreationDate());
    }

    /**
     * <p>
     * Failure Test for the setCreationDate.
     * </p>
     * <p>
     * date is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetCreationDate1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setCreationDate(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "creationDate should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Accuracy Test for the setModificationDate.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testAccuracySetModificationDate1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();
        Date now = new Date();
        screeningTask.setModificationDate(now);
        assertEquals("check modification date", now, screeningTask.getModificationDate());
    }

    /**
     * <p>
     * Failure Test for the setModificationDate.
     * </p>
     * <p>
     * date is null. IllegalArgumentException is expected.
     * </p>
     * @throws Exception
     *             throw any exception to JUnit
     */
    public void testFailureSetModificationDate1() throws Exception {
        ScreeningTask screeningTask = new ScreeningTask();

        try {
            screeningTask.setModificationDate(null);
        } catch (IllegalArgumentException e) {
            assertEquals("check message", "modificationDate should not be null.", e.getMessage());
        }
    }

}