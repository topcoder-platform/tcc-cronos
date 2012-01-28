/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.actions.GroupInvitationAwareBaseAction;
import com.topcoder.security.groups.actions.accuracytests.MockGroupInvitationService;
import com.topcoder.security.groups.services.GroupInvitationService;

/**
 * <p>
 * Accuracy test for GroupInvitationAwareBaseAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupInvitationAwareBaseActionAccurracyTest {
    /**
     * Represents the instance of GroupInvitationAwareBaseAction used in test.
     */
    private GroupInvitationAwareBaseAction instance;

    /**
     * Sets up for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        instance = new GroupInvitationAwareBaseAction() {
        };
    }

    /**
     * Tears down for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(GroupInvitationAwareBaseActionAccurracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for GroupInvitationAwareBaseAction(). The instance should
     * be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCtor() throws Exception {
        assertNotNull("The instance should be created.", instance);
    }

    /**
     * <p>
     * Accuracy test for GroupInvitationService property.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGroupInvitationService() throws Exception {
        GroupInvitationService groupInvitationService = new MockGroupInvitationService();
        instance.setGroupInvitationService(groupInvitationService);
        assertEquals("The groupInvitationService is incorrect.", groupInvitationService,
            instance.getGroupInvitationService());
    }
}