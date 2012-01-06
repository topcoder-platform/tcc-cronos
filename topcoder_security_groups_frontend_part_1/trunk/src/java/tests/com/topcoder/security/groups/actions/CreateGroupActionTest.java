/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * Unit tests for class <code>CreateGroupAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class CreateGroupActionTest {
    /**
     * <p>
     * Represents the <code>CreateGroupAction</code> instance used to test against.
     * </p>
     */
    private CreateGroupAction impl;

    /**
     * <p>
     * Represents the <code>GroupService</code> instance used in test.
     * </p>
     */
    private GroupService groupService;

    /**
     * <p>
     * Represents the <code>GroupAuditService</code> instance used in test.
     * </p>
     */
    private GroupAuditService auditService;

    /**
     * <p>
     * Represents the <code>GroupInvitationService</code> instance used in test.
     * </p>
     */
    private GroupInvitationService groupInvitationService;

    /**
     * <p>
     * Represents the <code>Group</code> instance used in test.
     * </p>
     */
    private Group group;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CreateGroupActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new CreateGroupAction();

        groupService = EasyMock.createNiceMock(GroupService.class);
        groupInvitationService = EasyMock.createNiceMock(GroupInvitationService.class);
        auditService = EasyMock.createNiceMock(GroupAuditService.class);

        group = new Group();
        group.setId(1);
        group.setName("group name");

        List<GroupMember> members = new ArrayList<GroupMember>();
        GroupMember member1 = new GroupMember();
        member1.setId(1);
        GroupMember member2 = new GroupMember();
        member2.setId(2);
        members.add(member1);
        members.add(member2);
        group.setGroupMembers(members);
        impl.setGroup(group);
    }

    /**
     * <p>
     * Tear down the test environment.
     * </p>
     */
    @After
    public void tearDown() {
        impl = null;
    }

    /**
     * <p>
     * Accuracy test for the method <code>createGroup()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testCreateGroup() throws Exception {
        groupService.add(group);
        EasyMock.expectLastCall().andReturn(0);
        groupInvitationService.addInvitation(EasyMock.anyObject(GroupInvitation.class));
        EasyMock.expectLastCall().times(2);
        EasyMock.replay(groupService, groupInvitationService);

        impl.setGroupService(groupService);
        impl.setGroupInvitationService(groupInvitationService);
        impl.setAuditService(auditService);

        assertEquals("The return value should be same as ", "success", impl.createGroup());

        EasyMock.verify(groupService, groupInvitationService);
    }

    /**
     * <p>
     * Failure test for the method <code>createGroup()</code>.<br>
     * SecurityGroupException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testCreateGroupFailure() throws Exception {
        groupService.add(group);
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("for test"));
        groupInvitationService.addInvitation(EasyMock.anyObject(GroupInvitation.class));
        EasyMock.expectLastCall().times(2);
        EasyMock.replay(groupService, groupInvitationService);

        impl.setGroupService(groupService);
        impl.setGroupInvitationService(groupInvitationService);

        impl.createGroup();
    }

    /**
     * <p>
     * Failure test for the method <code>createGroup()</code>.<br>
     * RuntimeException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testCreateGroupFailure2() throws Exception {
        groupService.add(group);
        EasyMock.expectLastCall().andReturn(0);
        groupInvitationService.addInvitation(EasyMock.anyObject(GroupInvitation.class));
        EasyMock.expectLastCall().andThrow(new RuntimeException("for test"));
        EasyMock.replay(groupService, groupInvitationService);

        impl.setGroupService(groupService);
        impl.setGroupInvitationService(groupInvitationService);

        impl.createGroup();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>CreateGroupAction()</code>. Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(impl);
    }

    /**
     * <p>
     * <code>CreateGroupAction</code> should be subclass of <code>superClassName</code>.
     * </p>
     */
    @Test
    public void testInheritance0() {
        assertTrue("CreateGroupAction should be subclass of CreateUpdateGroupAction.",
                CreateGroupAction.class.getSuperclass() == CreateUpdateGroupAction.class);
    }

    /**
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    private void fillField(Group instance) {
        instance.setName("name");
        instance.setDefaultPermission(GroupPermissionType.READ);
        Client client = new Client();
        client.setName("clientName");
        instance.setClient(client);
        List<BillingAccount> billingAccounts = new ArrayList<BillingAccount>();
        billingAccounts.add(new BillingAccount());
        List<DirectProject> directProject = new ArrayList<DirectProject>();
        directProject.add(new DirectProject());
        List<ResourceType> resourceType = new ArrayList<ResourceType>();
        resourceType.add(ResourceType.PROJECT);
        instance.setBillingAccounts(billingAccounts);
        instance.setDirectProjects(directProject);
        instance.setRestrictions(resourceType);

    }

    /**
     * create a mock instance of CreateGroupAction.
     *
     * @return the SearchGroupAction instance
     */
    @SuppressWarnings("serial")
    private CreateGroupAction createMockInstance() {
        CreateGroupAction instance = new CreateGroupAction() {
            @Override
            public String getText(String textName) {
                return "mocked";
            }
        };

        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        instance.setGroupMembers(groupMembers);

        return instance;
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * name is null.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate2() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.setName(null);
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * defaultPermission is null.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate3() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.setDefaultPermission(null);
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * client name is null.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate4() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.getClient().setName(null);
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * billingAccounts is null.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate5() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.setBillingAccounts(null);
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * directProjects is null.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate6() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.setDirectProjects(null);
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * restrictions is null.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate7() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.setRestrictions(null);
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * member is invalid<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate8() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        instance.setGroup(group2);
        GroupMember groupMember = new GroupMember();
        instance.getGroupMembers().add(groupMember);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * restrictions is empty.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate9() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.getRestrictions().clear();
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * name is empty.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate10() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        group2.setName(" ");
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * name's length is bigger than 45.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate11() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        char[] data = new char[46];
        Arrays.fill(data, 'a');
        group2.setName(String.valueOf(data));
        instance.setGroup(group2);
        instance.validate();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test
    public void testValidate12() {
        CreateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        instance.setGroup(group2);
        GroupMember groupMember = new GroupMember();
        groupMember.setSpecificPermission(GroupPermissionType.READ);
        instance.getGroupMembers().add(groupMember);
        instance.validate();
    }
}
