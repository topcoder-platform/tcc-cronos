/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.services.GroupInvitationService;

/**
 * <p>
 * Unit tests for the {@link GroupInvitationAwareBaseAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupInvitationAwareBaseActionTest {
    /** Represents the instance used to test again. */
    private GroupInvitationAwareBaseAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        testInstance = new GroupInvitationAwareBaseAction(){};
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }
    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(GroupInvitationAwareBaseActionTest.class);
    }
    /**
     * <p>
     * Accuracy test for {@link GroupInvitationAwareBaseAction#GroupInvitationAwareBaseAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGroupInvitationAwareBaseAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>GroupInvitationAwareBaseAction</code> subclasses
     * <code>BaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue("GroupInvitationAwareBaseAction does not subclass BaseAction.",
            GroupInvitationAwareBaseAction.class.getSuperclass() == BaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationAwareBaseAction#getGroupInvitationService()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getGroupInvitationService() throws Exception {

    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationAwareBaseAction#checkInit()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_checkInit() throws Exception {

    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationAwareBaseAction#setGroupInvitationService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGroupInvitationService() throws Exception {
        GroupInvitationService groupInvitationService = EasyMock.createMock(GroupInvitationService.class);
        testInstance.setGroupInvitationService(groupInvitationService);
        assertEquals("Should be equals", groupInvitationService, TestHelper.getField(testInstance,
                "groupInvitationService"));
    }
}