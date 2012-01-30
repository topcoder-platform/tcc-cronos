/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import junit.framework.JUnit4TestAdapter;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.actions.AccessAuditingInfoAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupMemberSearchCriteria;

/**
 * <p>
 * Failure unit tests for the {@link AccessAuditingInfoAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class AccessAuditingInfoActionFailureUnitTests {
    /**
     * <p>
     * private AccessAuditingInfoAction instance used in testing.
     * </p>
     */
    private AccessAuditingInfoAction instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(AccessAuditingInfoActionFailureUnitTests.class);
    }

    /**
     * Sets up test environment.
     */
    @Before
    public void setUp() {
        instance = new AccessAuditingInfoAction();
        instance.setAuditService(EasyMock.createMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createMock(AuthorizationService.class));
        instance.setClientService(EasyMock.createMock(ClientService.class));
        instance.setCustomerAdministratorService(EasyMock.createMock(CustomerAdministratorService.class));
        instance.setGroupMemberService(EasyMock.createMock(GroupMemberService.class));
        instance.setGroupService(EasyMock.createMock(GroupService.class));
        instance.setDirectProjectService(EasyMock.createMock(DirectProjectService.class));
        instance.setUserService(EasyMock.createMock(UserService.class));
    }

    /**
     * Tears down test environment.
     */
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * <p>
     * Failure test for {@link AccessAuditingInfoAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecuteFail1() throws Exception {
        GroupMemberService groupMemberService = EasyMock.createMock(GroupMemberService.class);
        EasyMock.expect(groupMemberService.searchHistoricalData(
                EasyMock.capture(new Capture<GroupMemberSearchCriteria>()), EasyMock.anyInt(),
                EasyMock.anyInt())).andThrow(new SecurityGroupException("error"));
        instance.setGroupMemberService(groupMemberService);
        EasyMock.replay(groupMemberService);
        try {
            instance.execute();
        } finally {
            EasyMock.verify(groupMemberService);
        }
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure1() throws Exception {
        instance.setGroupMemberService(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure2() throws Exception {
        instance.setGroupService(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure3() throws Exception {
        instance.setDirectProjectService(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure4() throws Exception {
        instance.setUserService(null);
        instance.checkInit();
    }
}
