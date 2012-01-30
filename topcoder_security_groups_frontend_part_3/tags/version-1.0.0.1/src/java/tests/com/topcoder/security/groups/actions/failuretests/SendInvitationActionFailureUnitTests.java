/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.util.Arrays;

import junit.framework.JUnit4TestAdapter;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.SendInvitationAction;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Failure unit tests for the {@link SendInvitationAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class SendInvitationActionFailureUnitTests {
    /**
     * <p>
     * private SendInvitationAction instance used in testing.
     * </p>
     */
    private SendInvitationAction instance;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SendInvitationActionFailureUnitTests.class);
    }

    /**
     * Sets up test environment.
     */
    @Before
    public void setUp() {
        instance = new SendInvitationAction();
        instance.setAuditService(EasyMock.createMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createMock(AuthorizationService.class));
        instance.setCustomerAdministratorService(EasyMock.createMock(CustomerAdministratorService.class));
        instance.setUserService(EasyMock.createMock(UserService.class));
        instance.setGroupService(EasyMock.createMock(GroupService.class));
        instance.setGroupInvitationService(EasyMock.createMock(GroupInvitationService.class));
        instance.setCustomerAdministratorService(EasyMock.createMock(CustomerAdministratorService.class));
        instance.setRegistrationUrl("url");
        instance.setAcceptRejectUrlBase("url");
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
     * Failure test for {@link SendInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecuteFail1() throws Exception {
        instance.setGroupNames(Arrays.asList("group1", "group2"));
        instance.setHandles(Arrays.asList("handle1", "handle2"));
        GroupService groupService = EasyMock.createMock(GroupService.class);
        instance.setGroupService(groupService);
        EasyMock.expect(groupService.search(
                EasyMock.capture(new Capture<GroupSearchCriteria>()), EasyMock.anyInt(), EasyMock.anyInt())).andThrow(
                        new SecurityGroupException("error"));
        EasyMock.replay(groupService);
        try {
            instance.execute();
        } finally {
            EasyMock.verify(groupService);
        }
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecuteFail2() throws Exception {
        instance.setGroupNames(Arrays.asList("group1", "group2"));
        instance.setHandles(Arrays.asList("handle1", "handle2"));
        GroupService groupService = EasyMock.createMock(GroupService.class);
        instance.setGroupService(groupService);
        PagedResult<Group> result = new PagedResult<Group>();
        result.setValues(Arrays.asList(new Group(), new Group()));
        EasyMock.expect(groupService.search(
                EasyMock.capture(new Capture<GroupSearchCriteria>()), EasyMock.anyInt(),
                EasyMock.anyInt())).andReturn(result);
        EasyMock.replay(groupService);
        try {
            instance.execute();
        } finally {
            EasyMock.verify(groupService);
        }
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecuteFail3() throws Exception {
        instance.setGroupNames(Arrays.asList("group1", "group2"));
        instance.setHandles(Arrays.asList("handle1", "handle2"));
        GroupService groupService = EasyMock.createMock(GroupService.class);
        UserService userService = EasyMock.createMock(UserService.class);
        instance.setGroupService(groupService);
        instance.setUserService(userService);
        PagedResult<Group> result = new PagedResult<Group>();
        result.setValues(Arrays.asList(new Group()));
        EasyMock.expect(groupService.search(
                EasyMock.capture(new Capture<GroupSearchCriteria>()), EasyMock.anyInt(),
                EasyMock.anyInt())).andReturn(result);
        EasyMock.expect(userService.search("handle1")).andThrow(new SecurityGroupException("error."));
        EasyMock.replay(groupService);
        EasyMock.replay(userService);
        try {
            instance.execute();
        } finally {
            EasyMock.verify(groupService);
            EasyMock.verify(userService);
        }
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecuteFail4() throws Exception {
        instance.setGroupNames(Arrays.asList("group1", "group2"));
        instance.setHandles(Arrays.asList("handle1", "handle2"));
        GroupService groupService = EasyMock.createMock(GroupService.class);
        UserService userService = EasyMock.createMock(UserService.class);
        instance.setGroupService(groupService);
        instance.setUserService(userService);
        PagedResult<Group> result = new PagedResult<Group>();
        result.setValues(Arrays.asList(new Group()));
        EasyMock.expect(groupService.search(
                EasyMock.capture(new Capture<GroupSearchCriteria>()), EasyMock.anyInt(),
                EasyMock.anyInt())).andReturn(result);
        EasyMock.expect(userService.search("handle1")).andReturn(Arrays.asList(new UserDTO(), new UserDTO()));
        EasyMock.replay(groupService);
        EasyMock.replay(userService);
        try {
            instance.execute();
        } finally {
            EasyMock.verify(groupService);
            EasyMock.verify(userService);
        }
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#input()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testInputFail() throws Exception {
        GroupService groupService = EasyMock.createMock(GroupService.class);
        instance.setGroupService(groupService);
        EasyMock.expect(groupService.search(EasyMock.capture(new Capture<GroupSearchCriteria>()),
                EasyMock.anyInt(), EasyMock.anyInt())).andThrow(new SecurityGroupException("error"));
        EasyMock.replay(groupService);
        try {
            instance.input();
        } finally {
            EasyMock.verify(groupService);
        }
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure1() throws Exception {
        instance.setGroupService(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure2() throws Exception {
        instance.setGroupInvitationService(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure3() throws Exception {
        instance.setUserService(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure4() throws Exception {
        instance.setCustomerAdministratorService(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure5() throws Exception {
        instance.setRegistrationUrl(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure6() throws Exception {
        instance.setRegistrationUrl("");
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure7() throws Exception {
        instance.setRegistrationUrl("    ");
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure8() throws Exception {
        instance.setAcceptRejectUrlBase(null);
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure9() throws Exception {
        instance.setAcceptRejectUrlBase("");
        instance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link SendInvitationAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInitFailure10() throws Exception {
        instance.setAcceptRejectUrlBase("    ");
        instance.checkInit();
    }
}
