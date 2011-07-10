/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for ContactInfoTaskChecker.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContactInfoTaskCheckerUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents ContactInfoTaskChecker instance for testing.
     * </p>
     */
    private ContactInfoTaskChecker checker;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        checker = (ContactInfoTaskChecker) getBean("contactInfoTaskChecker");
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        MockFactory.resetDAOs();
        checker = null;
    }

    /**
     * <p>
     * Tests ContactInfoTaskChecker constructor.
     * </p>
     * <p>
     * ContactInfoTaskChecker instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("ContactInfoTaskChecker instance should be created successfully.", checker);
    }

    /**
     * <p>
     * Tests ContactInfoTaskChecker#getTaskReport() method with valid fields and user passed.
     * </p>
     * <p>
     * Report should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTaskReport1() throws Exception {
        User user = MockFactory.getUser();
        ProfileTaskReport report = checker.getTaskReport(user);
        int expectedTotalFieldCount = 1;
        int expectedCompletedFieldCount = 1;
        assertEquals("Total field count should be valid.", expectedTotalFieldCount, report.getTotalFieldCount());
        assertEquals("Completed field count should be valid.", expectedCompletedFieldCount,
                report.getCompletedFieldCount());
        assertEquals("Report should have valid completed status.",
                expectedCompletedFieldCount == expectedTotalFieldCount, report.getCompleted());
    }

    /**
     * <p>
     * Tests ContactInfoTaskChecker#getTaskReport() method with valid fields and user passed, with some uncompleted
     * fields.
     * </p>
     * <p>
     * Report should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTaskReport2() throws Exception {
        User user = new User();
        ProfileTaskReport report = checker.getTaskReport(user);
        int expectedTotalFieldCount = 1;
        int expectedCompletedFieldCount = 0;
        assertEquals("Total field count should be valid.", expectedTotalFieldCount, report.getTotalFieldCount());
        assertEquals("Completed field count should be valid.", expectedCompletedFieldCount,
                report.getCompletedFieldCount());
        assertEquals("Report should have valid completed status.",
                expectedCompletedFieldCount == expectedTotalFieldCount, report.getCompleted());
    }

    /**
     * <p>
     * Tests ContactInfoTaskChecker#getTaskReport() method with null user passed.
     * </p>
     * <p>
     * IllegalArgumentException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTaskReport_Null_User() throws Exception {
        try {
            checker.getTaskReport(null);
            fail("IllegalArgumentException exception is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
