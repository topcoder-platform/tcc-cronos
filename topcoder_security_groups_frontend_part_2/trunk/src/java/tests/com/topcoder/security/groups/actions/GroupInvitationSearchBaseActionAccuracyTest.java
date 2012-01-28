/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.actions.GroupInvitationSearchBaseAction;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Accuracy test for GroupInvitationSearchBaseAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GroupInvitationSearchBaseActionAccuracyTest {
    /**
     * Represents the instance of GroupInvitationSearchBaseAction used in test.
     */
    private GroupInvitationSearchBaseAction instance;

    /**
     * Set up for each test.
     *
     * @throws Exception
     *             to JUnit
     */
    @SuppressWarnings("serial")
    @Before
    public void setUp() throws Exception {
        instance = new GroupInvitationSearchBaseAction() {
        };
    }

    /**
     * Tear down for each test.
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
        return new JUnit4TestAdapter(GroupInvitationSearchBaseActionAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test for GroupInvitationSearchBaseAction(). The instance should
     * be created correctly.
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
     * Accuracy test for Criteria property.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCriteria() throws Exception {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        instance.setCriteria(criteria);
        assertEquals("The criteria is incorrect.", criteria, instance.getCriteria());
    }

    /**
     * <p>
     * Accuracy test for PageSize property.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testPageSize() throws Exception {
        int pageSize = 10;
        instance.setPage(pageSize);
        assertEquals("The pageSize is incorrect.", pageSize, instance.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for Page property.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testPage() throws Exception {
        int page = 10;
        instance.setPage(page);
        assertEquals("The page is incorrect.", page, instance.getPage());
    }

    /**
     * <p>
     * Accuracy test for Invitations property.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testInvitations() throws Exception {
        PagedResult<GroupInvitation> invitations = new PagedResult<GroupInvitation>();
        instance.setInvitations(invitations);
        assertEquals("The page is incorrect.", invitations, instance.getInvitations());
    }
}