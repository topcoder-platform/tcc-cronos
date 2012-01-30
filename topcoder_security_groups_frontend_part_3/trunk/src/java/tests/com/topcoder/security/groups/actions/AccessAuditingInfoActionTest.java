/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.GroupMemberAccessHistoricalData;
import com.topcoder.security.groups.services.dto.GroupMemberSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;
import com.topcoder.security.groups.services.dto.ProjectDTO;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Unit tests for the {@link AccessAuditingInfoAction}.
 * </p>
 *
 * @author progloco
 * @version 1.0
 */
public class AccessAuditingInfoActionTest {
    /** Represents the instance used to test again. */
    private AccessAuditingInfoAction testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new AccessAuditingInfoAction();
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
        return new JUnit4TestAdapter(AccessAuditingInfoActionTest.class);
    }

    /**
     * <p>
     * Accuracy test for
     * {@link AccessAuditingInfoAction#AccessAuditingInfoAction()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testAccessAuditingInfoAction() throws Exception {
        assertNotNull("Instance should be correctly created.", testInstance);
    }

    /**
     * <p>
     * Inheritance test, verifies <code>AccessAuditingInfoAction</code>
     * subclasses <code>ClientsPrepopulatingBaseAction</code>.
     * </p>
     */
    @Test
    public void testInheritance() {
        assertTrue(
                "AccessAuditingInfoAction does not subclass ClientsPrepopulatingBaseAction.",
                AccessAuditingInfoAction.class.getSuperclass() == ClientsPrepopulatingBaseAction.class);
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate1() throws Exception {
        List<GroupPermissionType> permissions = new ArrayList<GroupPermissionType>();
        GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();
        criteria.setPermissions(permissions);
        criteria.setGroupName(" ");
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 2", 2, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate2() throws Exception {
        List<GroupPermissionType> permissions = new ArrayList<GroupPermissionType>();
        GroupPermissionType g = GroupPermissionType.FULL;
        permissions.add(g);
        permissions.add(g);
        GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();
        criteria.setPermissions(permissions);
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate3() throws Exception {
        List<GroupPermissionType> permissions = new ArrayList<GroupPermissionType>();
        GroupPermissionType g = GroupPermissionType.FULL;
        permissions.add(null);
        permissions.add(g);
        permissions.add(g);
        GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();
        criteria.setPermissions(permissions);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append("a");
        }
        criteria.setGroupName(sb.toString());
        criteria.setClientName("abc");
        testInstance.setPage(-1);
        testInstance.setPageSize(1);
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 3", 3, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate4() throws Exception {
        GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();
        criteria.setPermissions(null);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append("a");
        }
        criteria.setGroupName(sb.toString());
        criteria.setClientName("abc");
        testInstance.setPage(-1);
        testInstance.setPageSize(1);
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 3", 3, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#validate()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_validate5() throws Exception {
        GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();
        criteria.setPermissions(null);
        criteria.setGroupName("abc");
        criteria.setClientName("abc");
        criteria.setProjectName("abc");
        criteria.setBillingAccountName("abc");
        testInstance.setCriteria(criteria);
        testInstance.validate();
        assertEquals("Should be 1", 1, testInstance.getFieldErrors().size());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_execute() throws Exception {
        testInstance.setAuditService(EasyMock
                .createNiceMock(GroupAuditService.class));
        testInstance.setAuthorizationService(EasyMock
                .createNiceMock(AuthorizationService.class));

        GroupMemberService groupMemberService = EasyMock
                .createNiceMock(GroupMemberService.class);
        testInstance.setGroupMemberService(groupMemberService);

        DirectProjectService dpService = EasyMock
                .createNiceMock(DirectProjectService.class);
        EasyMock.expect(dpService.get(EasyMock.capture(new Capture<Long>())))
                .andReturn(new ProjectDTO()).anyTimes();
        testInstance.setDirectProjectService(dpService);

        UserService userService = EasyMock.createNiceMock(UserService.class);
        EasyMock.expect(userService.get(EasyMock.capture(new Capture<Long>())))
                .andReturn(new UserDTO()).anyTimes();
        testInstance.setUserService(userService);

        Group group = new Group();
        group.setGroupMembers(Arrays.asList(new GroupMember()));
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        EasyMock
                .expect(groupService.get(EasyMock.capture(new Capture<Long>())))
                .andReturn(group).anyTimes();
        testInstance.setGroupService(groupService);

        GroupMemberSearchCriteria criteria = new GroupMemberSearchCriteria();
        testInstance.setPageSize(10);
        testInstance.setCriteria(criteria);
        testInstance.setPage(0);

        PagedResult<GroupMemberAccessHistoricalData> data = new PagedResult<GroupMemberAccessHistoricalData>();

        List<GroupMemberAccessHistoricalData> values = new ArrayList<GroupMemberAccessHistoricalData>();
        GroupMemberAccessHistoricalData gd = new GroupMemberAccessHistoricalData();
        gd.setDirectProjectIds(Arrays.asList(1L));
        values.add(gd);
        data.setValues(values);
        EasyMock.expect(
                groupMemberService.searchHistoricalData(criteria, 10, 0))
                .andReturn(data);

        EasyMock.replay(userService);
        EasyMock.replay(dpService);
        EasyMock.replay(groupService);
        EasyMock.replay(groupMemberService);
        testInstance.execute();
        EasyMock.verify(groupMemberService);
        EasyMock.verify(groupService);
        EasyMock.verify(dpService);
        EasyMock.verify(userService);

        // Verify the execute result.
        assertEquals("Should be equals", data, testInstance.getHistoricalData());
        assertEquals("Groups size should be 1", 1, testInstance.getGroups()
                .size());
        assertEquals("Should be equals", group, testInstance.getGroups().get(0));
    }

    /**
     * <p>
     * Failure test for {@link AccessAuditingInfoAction#execute()}.
     * </p>
     * <p>
     * Exception in service, SecurityGroupsActionException should throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail1() throws Exception {
        GroupMemberService groupMemberService = EasyMock
                .createNiceMock(GroupMemberService.class);
        Capture<Integer> i = new Capture<Integer>();
        EasyMock.expect(
                groupMemberService.searchHistoricalData(EasyMock
                        .capture(new Capture<GroupMemberSearchCriteria>()),
                        EasyMock.capture(i), EasyMock.capture(i))).andThrow(
                new SecurityGroupException("test"));
        testInstance.setGroupMemberService(groupMemberService);

        EasyMock.replay(groupMemberService);
        testInstance.execute();
        EasyMock.verify(groupMemberService);
    }

    /**
     * <p>
     * Failure test for {@link AccessAuditingInfoAction#execute()}.
     * </p>
     * <p>
     * Exception in service, SecurityGroupsActionException should throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void test_execute_fail2() throws Exception {
        GroupMemberService groupMemberService = EasyMock
                .createNiceMock(GroupMemberService.class);
        PagedResult<GroupMemberAccessHistoricalData> pr = new PagedResult<GroupMemberAccessHistoricalData>();
        pr.setValues(Arrays.asList((GroupMemberAccessHistoricalData) (null)));
        Capture<Integer> i = new Capture<Integer>();
        EasyMock.expect(
                groupMemberService.searchHistoricalData(EasyMock
                        .capture(new Capture<GroupMemberSearchCriteria>()),
                        EasyMock.capture(i), EasyMock.capture(i)))
                .andReturn(pr);
        testInstance.setGroupMemberService(groupMemberService);

        EasyMock.replay(groupMemberService);
        testInstance.execute();
        EasyMock.verify(groupMemberService);
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#checkInit()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_checkInit() throws Exception {
        testInstance.setAuditService(EasyMock
                .createNiceMock(GroupAuditService.class));
        testInstance.setAuthorizationService(EasyMock
                .createNiceMock(AuthorizationService.class));

        testInstance.setClientService(EasyMock
                .createNiceMock(ClientService.class));
        testInstance.setCustomerAdministratorService(EasyMock
                .createNiceMock(CustomerAdministratorService.class));

        GroupMemberService groupMemberService = EasyMock
                .createNiceMock(GroupMemberService.class);
        testInstance.setGroupMemberService(groupMemberService);
        testInstance.setGroupService(EasyMock
                .createNiceMock(GroupService.class));
        testInstance.setDirectProjectService(EasyMock
                .createNiceMock(DirectProjectService.class));
        testInstance.setUserService(EasyMock.createNiceMock(UserService.class));
        testInstance.checkInit();
    }

    /**
     * <p>
     * Failure test for {@link AccessAuditingInfoAction#checkInit()}.
     * </p>
     * <p>
     * Some field is not injected, SecurityGroupsActionConfigurationException
     * should throw.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void test_checkInit_fail1() throws Exception {
        testInstance.checkInit();
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#setCriteria()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCriteria() throws Exception {
        testInstance.setCriteria(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "criteria"));
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#setPageSize()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPageSize() throws Exception {
        testInstance.setPageSize(1000);
        Assert.assertEquals("Should be 1000", 1000, TestHelper.getField(
                testInstance, "pageSize"));
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#setPage()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setPage() throws Exception {
        testInstance.setPage(100);
        Assert.assertEquals("Should be 100", 100, TestHelper.getField(
                testInstance, "page"));
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#getHistoricalData()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getHistoricalData() throws Exception {
        Assert.assertNull("Should be null", testInstance.getHistoricalData());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#getGroups()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getGroups() throws Exception {
        Assert.assertNull("Should be null", testInstance.getGroups());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#getProjectById()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectById() throws Exception {
        Assert.assertNull("Should be null", testInstance.getProjectById());
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#getUserById()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getUserById() throws Exception {
        Assert.assertNull("Should be null", testInstance.getUserById());
    }

    /**
     * <p>
     * Accuracy test for
     * {@link AccessAuditingInfoAction#setGroupMemberService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGroupMemberService() throws Exception {
        testInstance.setGroupMemberService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "groupMemberService"));
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#setGroupService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setGroupService() throws Exception {
        testInstance.setGroupService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "groupService"));
    }

    /**
     * <p>
     * Accuracy test for
     * {@link AccessAuditingInfoAction#setDirectProjectService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDirectProjectService() throws Exception {
        testInstance.setDirectProjectService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "directProjectService"));
    }

    /**
     * <p>
     * Accuracy test for {@link AccessAuditingInfoAction#setUserService()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setUserService() throws Exception {
        testInstance.setUserService(null);
        Assert.assertNull("Should be null", TestHelper.getField(testInstance,
                "userService"));
    }
}