/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.text.SimpleDateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.ViewPendingApprovalUserAction;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.util.log.LogManager;


/**
 * <p>Failure test case of {@link ViewPendingApprovalUserAction}.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class ViewPendingApprovalUserActionTest extends TestCase {
    /**
     * <p>The ViewPendingApprovalUserAction instance to test.</p>
     */
    private ViewPendingApprovalUserAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        instance = new ViewPendingApprovalUserAction();
        instance.setGroupInvitationService(new MockGroupInvitationService());
        instance.setLogger(LogManager.getLog("test"));
        instance.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        ;
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>Creates a test suite for the tests in this test case.</p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(ViewPendingApprovalUserActionTest.class);

        return suite;
    }

    /**
     * <p>Failure test for method ViewPendingApprovalUserAction#execute().</p>
     *
     * @throws Throwable to junit
     */
    public void test_execute1() throws Throwable {
        try {
            instance.setPage(2);
            instance.setClientId(2l);
            instance.execute();
            fail("exception expected");
        } catch (SecurityGroupsActionException e) {
            // pass
        }
    }

    /**
     * <p>Failure test for method ViewPendingApprovalUserAction#validate().</p>
     *
     * @throws Throwable to junit
     */
    public void test_validate1() throws Throwable {
        InvitationSearchCriteria criteria = getCriteria();
        criteria.setInviteeEmail("invalidemail@");
        instance.setCriteria(criteria);
        instance.validate();
        assertFalse("validation should fail", instance.getFieldErrors().isEmpty());
    }

    /**
     * <p>Failure test for method ViewPendingApprovalUserAction#validate().</p>
     *
     * @throws Throwable to junit
     */
    public void test_validate2() throws Throwable {
        InvitationSearchCriteria criteria = getCriteria();
        criteria.setInviteeHandle(" ");
        instance.setCriteria(criteria);
        instance.validate();
        assertFalse("validation should fail", instance.getFieldErrors().isEmpty());
    }

    /**
     * <p>Failure test for method ViewPendingApprovalUserAction#validate().</p>
     *
     * @throws Throwable to junit
     */
    public void test_validate3() throws Throwable {
        InvitationSearchCriteria criteria = getCriteria();
        criteria.setSentDateFrom((new SimpleDateFormat("yyyy-mm-dd")).parse("2011-01-01"));
        criteria.setSentDateTo((new SimpleDateFormat("yyyy-mm-dd")).parse("2001-01-01"));
        instance.setCriteria(criteria);
        instance.validate();
        assertFalse("validation should fail", instance.getFieldErrors().isEmpty());
    }

    /**
     * Correct criteria.
     *
     * @return criteria
     *
     * @throws Throwable to junit
     */
    private InvitationSearchCriteria getCriteria() throws Throwable {
        InvitationSearchCriteria criteria = new InvitationSearchCriteria();
        criteria.setGroupName("test");
        criteria.setInviteeHandle("handle");
        criteria.setInviteeEmail("tc@tc.com");

        return criteria;
    }
}
