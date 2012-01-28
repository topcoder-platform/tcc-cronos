/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Unit tests for the {@link GroupInvitationSearchBaseAction}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupInvitationSearchBaseActionTest {
    /** Represents the instance used to test again. */
    private GroupInvitationSearchBaseAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        testInstance = new GroupInvitationSearchBaseAction() {
        };
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
        return new JUnit4TestAdapter(GroupInvitationSearchBaseActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#validate()} .
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testValidate1() {
        testInstance.setPage(1);
        testInstance.setPageSize(-1);
        assertEquals("Should be 1.", testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#validate()} .
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testValidate2() {
        testInstance.setPage(-1);
        assertEquals("Should be 1.", testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link GroupInvitationSearchBaseAction#GroupInvitationSearchBaseAction()}
     * .
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testGroupInvitationSearchBaseAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>GroupInvitationSearchBaseAction</code>
     * subclasses <code>GroupInvitationAwareBaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "GroupInvitationSearchBaseAction does not subclass GroupInvitationAwareBaseAction.",
                GroupInvitationSearchBaseAction.class.getSuperclass() == GroupInvitationAwareBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#getCriteria()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getCriteria() throws Exception {
        assertNull("Should be null", testInstance.getCriteria());
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#setCriteria()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCriteria() throws Exception {
        InvitationSearchCriteria c = new InvitationSearchCriteria();
        testInstance.setCriteria(c);
        assertEquals("Should be equals", c, testInstance.getCriteria());
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#getPageSize()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPageSize() throws Exception {
        assertEquals("Should be 10", 10, testInstance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#setPageSize()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPageSize() throws Exception {
        int pageSize = 100;
        testInstance.setPageSize(pageSize);
        assertEquals("Should be 100", 100, testInstance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#getPage()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getPage() throws Exception {
        assertEquals("Should be 1", 1, testInstance.getPage());
    }

    /**
     * <p>
     * Accuracy test for {@link GroupInvitationSearchBaseAction#setPage()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPage() throws Exception {
        int page = 100;
        testInstance.setPage(page);
        assertEquals("Should be 100", 100, testInstance.getPage());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link GroupInvitationSearchBaseAction#getInvitations()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getInvitations() throws Exception {
        PagedResult<GroupInvitation> pr = new PagedResult<GroupInvitation>();
        testInstance.setInvitations(pr);
        assertEquals("Should be equals", pr, testInstance.getInvitations());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link GroupInvitationSearchBaseAction#setInvitations()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setInvitations() throws Exception {
        assertNull("Should be null", testInstance.getInvitations());
    }
}