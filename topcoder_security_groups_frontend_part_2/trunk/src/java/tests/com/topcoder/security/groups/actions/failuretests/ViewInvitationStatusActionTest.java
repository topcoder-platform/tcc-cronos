/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.ViewInvitationStatusAction;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.dto.InvitationSearchCriteria;
import com.topcoder.util.log.LogManager;


/**
 * <p>Failure test case of {@link ViewInvitationStatusAction}.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class ViewInvitationStatusActionTest extends TestCase {
    /**
     * <p>The ViewInvitationStatusAction instance to test.</p>
     */
    private ViewInvitationStatusAction instance;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        instance = new ViewInvitationStatusAction();
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
        TestSuite suite = new TestSuite(ViewInvitationStatusActionTest.class);

        return suite;
    }

    /**
     * <p>Failure test for method ViewInvitationStatusAction#execute().</p>
     *
     * @throws Throwable to junit
     */
    public void test_execute1() throws Throwable {
        try {
            instance.setPage(2);
            instance.execute();
            fail("exception expected");
        } catch (SecurityGroupsActionException e) {
            // pass
        }
    }

    /**
     * <p>Failure test for method ViewInvitationStatusAction#validate().</p>
     *
     * @throws Throwable to junit
     */
    public void test_validate1() throws Throwable {
        InvitationSearchCriteria criteria = getCriteria();
        criteria.setGroupName(" ");
        instance.setCriteria(criteria);
        instance.validate();
        assertFalse("validation should fail", instance.getFieldErrors().isEmpty());
    }

    /**
     * <p>Failure test for method ViewInvitationStatusAction#validate().</p>
     *
     * @throws Throwable to junit
     */
    public void test_validate2() throws Throwable {
        InvitationSearchCriteria criteria = getCriteria();
        criteria.setAcceptedDateTo((new SimpleDateFormat("yyyy-mm-dd")).parse("2001-01-01"));
        instance.setCriteria(criteria);
        instance.validate();
        assertFalse("validation should fail", instance.getFieldErrors().isEmpty());
    }

    /**
     * <p>Failure test for method ViewInvitationStatusAction#validate().</p>
     *
     * @throws Throwable to junit
     */
    public void test_validate3() throws Throwable {
        InvitationSearchCriteria criteria = getCriteria();
        criteria.setSentDateTo((new SimpleDateFormat("yyyy-mm-dd")).parse("2001-01-01"));
        instance.setCriteria(criteria);
        instance.validate();
        assertFalse("validation should fail", instance.getFieldErrors().isEmpty());
    }

    /**
     * <p>Failure test for method ViewInvitationStatusAction#validate().</p>
     *
     * @throws Throwable to junit
     */
    public void test_validate4() throws Throwable {
        InvitationSearchCriteria criteria = getCriteria();
        List<GroupPermissionType> permissions = new ArrayList<GroupPermissionType>();
        permissions.add(GroupPermissionType.READ);
        permissions.add(GroupPermissionType.READ);
        criteria.setPermissions(permissions);
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

        List<GroupPermissionType> permissions = new ArrayList<GroupPermissionType>();
        permissions.add(GroupPermissionType.READ);
        permissions.add(GroupPermissionType.FULL);
        criteria.setPermissions(permissions);

        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        criteria.setSentDateFrom(df.parse("2011-09-09"));
        criteria.setSentDateTo(df.parse("2011-10-09"));
        criteria.setAcceptedDateFrom(df.parse("2011-09-09"));
        criteria.setAcceptedDateTo(df.parse("2011-10-09"));

        return criteria;
    }
}
