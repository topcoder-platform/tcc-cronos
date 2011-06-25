/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.jivesoftware.forum.ForumFactory;

/**
 * <p>
 * This class contains Unit tests for BaseForumAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseForumActionParametersUnitTest extends BaseUnitTest {

    /**
     * <p>
     * Represents BaseForumAction action for testing.
     * </p>
     */
    private BaseForumAction action;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        action = new MockBaseForumAction();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        action = null;
    }

    /**
     * <p>
     * Tests BaseForumAction constructor.
     * </p>
     * <p>
     * BaseForumAction instance should be created successfully. No exception is expected.
     * </p>
     */
    public void testConstructor() {
        assertNotNull("BaseForumAction instance should be created successfully.", action);
    }

    /**
     * <p>
     * Tests BaseForumAction#getForumFactory() method.
     * </p>
     * <p>
     * forumFactory should be retrieved successfully. No exception is expected.
     * </p>
     */
    public void testGetForumFactory() {
        assertNull("forumFactory should be null.", action.getForumFactory());
        ForumFactory forumFactory = getForumFactory();
        action.setForumFactory(forumFactory);
        assertSame("getForumFactory() doesn't work properly.", forumFactory, action.getForumFactory());
    }

    /**
     * <p>
     * Tests BaseForumAction#setForumFactory(ForumFactory) method.
     * </p>
     * <p>
     * forumFactory should be set successfully. No exception is expected.
     * </p>
     */
    public void testSetForumFactory() {
        assertNull("forumFactory should be null.", action.getForumFactory());
        ForumFactory forumFactory = getForumFactory();
        action.setForumFactory(forumFactory);
        assertSame("setForumFactory() doesn't work properly.", forumFactory, action.getForumFactory());
    }

    /**
     * <p>
     * Tests BlackListAction#prepare() method with null forumFactory attribute.
     * </p>
     * <p>
     * UserPreferencesActionExecutionException exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testPrepare_Null_ForumFactory() throws Exception {
        action.setForumFactory(null);
        try {
            action.prepare();
            fail("UserPreferencesActionConfigurationException exception is expected.");
        } catch (UserPreferencesActionConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * This is mock implementation of BaseForumAction.
     * </p>
     * @author TCSDEVELOPER
     * @version 1.0
     */
    @SuppressWarnings("serial")
    private class MockBaseForumAction extends BaseForumAction {
        // do nothing
    }
}
