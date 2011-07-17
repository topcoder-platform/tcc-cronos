/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.BaseUnitTest;
import com.topcoder.web.reg.ProfileActionConfigurationException;
import com.topcoder.web.reg.mock.MockFactory;

/**
 * <p>
 * This class contains Unit tests for DefaultProfileCompletenessRetriever.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DefaultProfileCompletenessRetrieverUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents DefaultProfileCompletenessRetriever instance for testing.
     * </p>
     */
    private DefaultProfileCompletenessRetriever retriever;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        MockFactory.initDAOs();
        retriever = (DefaultProfileCompletenessRetriever) getBean("profileCompletenessRetriever");
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
        retriever = null;
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever constructor.
     * </p>
     * <p>
     * DefaultProfileCompletenessRetriever instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("DefaultProfileCompletenessRetriever instance should be created successfully.", retriever);
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever#checkInitialization() method with valid fields.
     * </p>
     * <p>
     * check initialization should pass successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization() throws Exception {
        retriever.checkInitialization();
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever#getProfileCompletenessReport() method with null user passed.
     * </p>
     * <p>
     * IllegalArgumentException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetTaskReport_Null_User() throws Exception {
        try {
            retriever.getProfileCompletenessReport(null);
            fail("IllegalArgumentException exception is expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever#getProfileCompletenessReport(User) method with valid fields and
     * arguments passed.
     * </p>
     * <p>
     * Report should be retrieved successfully. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetProfileCompletenessReport() throws Exception {
        User user = MockFactory.createUser(1L, "first", "last", "handle");
        int totalFieldCount = 3;
        int completedFieldCount = 1;
        int checkersCount = 3;
        String taskName = "task";
        ProfileTaskChecker checker = retriever.getProfileTaskCheckers().get(0);
        ProfileTaskReport profileTaskReport = new ProfileTaskReport();
        profileTaskReport.setCompleted(true);
        profileTaskReport.setCompletedFieldCount(completedFieldCount);
        profileTaskReport.setTaskName(taskName);
        profileTaskReport.setTotalFieldCount(totalFieldCount);
        when(checker.getTaskReport(user)).thenReturn(profileTaskReport);
        // add 2 more checkers
        retriever.getProfileTaskCheckers().add(checker);
        retriever.getProfileTaskCheckers().add(checker);
        ProfileCompletenessReport report = retriever.getProfileCompletenessReport(user);
        assertEquals("Percentage should be valid.", completedFieldCount * 100 / totalFieldCount,
                report.getCompletionPercentage());
        assertEquals("Statuses count should be valid.", checkersCount, report.getTaskCompletionStatuses().size());
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever#checkInitialization() method with null profileTaskCheckers.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_ProfileTaskCheckers() throws Exception {
        retriever.setProfileTaskCheckers(null);
        try {
            retriever.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever#checkInitialization() method with null profileTaskCheckers.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Null_Element() throws Exception {
        List<ProfileTaskChecker> checkers = new ArrayList<ProfileTaskChecker>();
        checkers.add(null);
        retriever.setProfileTaskCheckers(checkers);
        try {
            retriever.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests DefaultProfileCompletenessRetriever#checkInitialization() method with empty profileTaskCheckers.
     * </p>
     * <p>
     * ProfileActionConfigurationException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testCheckInitialization_Empty_ProfileTaskCheckers() throws Exception {
        retriever.setProfileTaskCheckers(new ArrayList<ProfileTaskChecker>());
        try {
            retriever.checkInitialization();
            fail("ProfileActionConfigurationException exception is expected.");
        } catch (ProfileActionConfigurationException e) {
            // expected
        }
    }
}
