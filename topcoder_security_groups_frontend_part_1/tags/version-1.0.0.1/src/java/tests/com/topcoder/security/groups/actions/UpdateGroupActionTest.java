/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * Unit tests for class <code>UpdateGroupAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class UpdateGroupActionTest {
    /**
     * <p>
     * Represents the <code>UpdateGroupAction</code> instance used to test against.
     * </p>
     */
    private UpdateGroupAction impl;

    /**
     * <p>
     * Represents the <code>GroupService</code> instance used in test.
     * </p>
     */
    private GroupService groupService;

    /**
     * <p>
     * Represents the <code>GroupInvitationService</code> instance used in test.
     * </p>
     */
    private GroupInvitationService groupInvitationService;

    /**
     * <p>
     * Represents the <code>GroupMemberService</code> instance used in test.
     * </p>
     */
    private GroupMemberService groupMemberService;

    /**
     * <p>
     * Represents the <code>GroupAuditService</code> instance used in test.
     * </p>
     */
    private GroupAuditService auditService;

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
        return new JUnit4TestAdapter(UpdateGroupActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new UpdateGroupAction();

        groupService = EasyMock.createNiceMock(GroupService.class);
        groupInvitationService = EasyMock.createNiceMock(GroupInvitationService.class);
        groupMemberService = EasyMock.createNiceMock(GroupMemberService.class);
        auditService = EasyMock.createNiceMock(GroupAuditService.class);

        group = new Group();
        group.setId(1);
        group.setName("group name");

        List<GroupMember> members = new ArrayList<GroupMember>();
        GroupMember member1 = new GroupMember();
        member1.setId(1);
        GroupMember member2 = new GroupMember();
        member2.setId(2);
        GroupMember member3 = new GroupMember();
        member3.setId(3);
        members.add(member1);
        members.add(member2);
        members.add(member3);
        group.setGroupMembers(members);

        List<GroupMember> oldMembers = new ArrayList<GroupMember>();
        oldMembers.add(member3);

        String groupSessionKey = "groupSessionKey";
        String oldGroupMembersSessionKey = "oldGroupMembersSessionKey";
        impl.setGroupSessionKey(groupSessionKey);
        impl.setOldGroupMembersSessionKey(oldGroupMembersSessionKey);
        HashMap<String, Object> session = new HashMap<String, Object>();
        session.put(groupSessionKey, group);
        session.put(oldGroupMembersSessionKey, oldMembers);
        impl.setSession(session);

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
     * Accuracy test for the method <code>updateGroup()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testUpdateGroup() throws Exception {
        Group newGroup = new Group();
        newGroup.setName("newGroupName");
        impl.setGroup(newGroup);
        impl.setGroupId(2);

        groupService.update(group);
        EasyMock.expectLastCall().once();
        groupMemberService.update(EasyMock.anyObject(GroupMember.class));
        EasyMock.expectLastCall().times(3);

        groupInvitationService.addInvitation(EasyMock.anyObject(GroupInvitation.class));
        EasyMock.expectLastCall().times(2);

        groupInvitationService.sendInvitation(EasyMock.anyObject(GroupInvitation.class),
                EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                EasyMock.anyObject(String.class));
        EasyMock.expectLastCall().times(2);
        EasyMock.replay(groupService, groupMemberService, groupInvitationService);

        impl.setGroupService(groupService);
        impl.setGroupMemberService(groupMemberService);
        impl.setGroupInvitationService(groupInvitationService);
        impl.setAuditService(auditService);

        assertEquals("The return value should be same as ", "success", impl.updateGroup());

        EasyMock.verify(groupService, groupMemberService, groupInvitationService);

    }

    /**
     * <p>
     * Accuracy test for the method <code>enterGroupDetails()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testEnterGroupDetails() throws Exception {
        impl.setGroupId(10);
        EasyMock.expect(groupService.get(10)).andReturn(group).once();

        EasyMock.replay(groupService);

        impl.setGroupService(groupService);

        assertEquals("The return value should be same as ", "success", impl.enterGroupDetails());

        EasyMock.verify(groupService);

        assertEquals("group should be same", group, impl.getSession().get(impl.getGroupSessionKey()));
        assertEquals("groupMembers should be same", group.getGroupMembers(),
                impl.getSession().get(impl.getOldGroupMembersSessionKey()));
    }

    /**
     * <p>
     * Failure test for the method <code>enterGroupDetails()</code> .<br>
     * RuntimeException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testEnterGroupDetails2() throws Exception {
        impl.setGroupId(10);
        EasyMock.expect(groupService.get(10)).andThrow(new RuntimeException("for test"));

        EasyMock.replay(groupService);

        impl.setGroupService(groupService);

        impl.enterGroupDetails();
    }

    /**
     * <p>
     * Failure test for the method <code>enterGroupDetails()</code> .<br>
     * SecurityGroupException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testEnterGroupDetails3() throws Exception {
        impl.setGroupId(10);
        EasyMock.expect(groupService.get(10)).andThrow(new SecurityGroupException("for test"));

        EasyMock.replay(groupService);

        impl.setGroupService(groupService);

        impl.enterGroupDetails();
    }

    /**
     * <p>
     * Failure test for the method <code>updateGroup()</code>.<br>
     * RuntimeException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testUpdateGroupFailure() throws Exception {
        groupService.update(group);
        EasyMock.expectLastCall().once();
        groupMemberService.update(EasyMock.anyObject(GroupMember.class));
        EasyMock.expectLastCall().times(3);

        groupInvitationService.addInvitation(EasyMock.anyObject(GroupInvitation.class));
        EasyMock.expectLastCall().andThrow(new RuntimeException("for test"));

        groupInvitationService.sendInvitation(EasyMock.anyObject(GroupInvitation.class),
                EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                EasyMock.anyObject(String.class));
        EasyMock.expectLastCall().times(2);
        EasyMock.replay(groupService, groupMemberService, groupInvitationService);

        impl.setGroupService(groupService);
        impl.setGroupMemberService(groupMemberService);
        impl.setGroupInvitationService(groupInvitationService);

        impl.updateGroup();
    }

    /**
     * <p>
     * Failure test for the method <code>updateGroup()</code>.<br>
     * SecurityGroupException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testUpdateGroupFailure2() throws Exception {
        groupService.update(group);
        EasyMock.expectLastCall().once();
        groupMemberService.update(EasyMock.anyObject(GroupMember.class));
        EasyMock.expectLastCall().times(3);

        groupInvitationService.addInvitation(EasyMock.anyObject(GroupInvitation.class));
        EasyMock.expectLastCall().times(2);

        groupInvitationService.sendInvitation(EasyMock.anyObject(GroupInvitation.class),
                EasyMock.anyObject(String.class), EasyMock.anyObject(String.class),
                EasyMock.anyObject(String.class));
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("for test"));
        EasyMock.replay(groupService, groupMemberService, groupInvitationService);

        impl.setGroupService(groupService);
        impl.setGroupMemberService(groupMemberService);
        impl.setGroupInvitationService(groupInvitationService);

        impl.updateGroup();
    }

    /**
     * <p>
     * <code>UpdateGroupAction</code> should be subclass of <code>superClassName</code>.
     * </p>
     */
    @Test
    public void testInheritance0() {
        assertTrue("UpdateGroupAction should be subclass of CreateUpdateGroupAction.",
                UpdateGroupAction.class.getSuperclass() == CreateUpdateGroupAction.class);
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ViewGroupAction()</code>. Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        impl = new UpdateGroupAction();
        assertNotNull(impl);
        assertEquals("'groupId' should be correct.", 0, impl.getGroupId());
        assertNull("'group' should be correct.", impl.getGroup());
        assertNull("'groupService' should be correct.", impl.getGroupService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupId()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupId() {
        long value = 42;
        impl.setGroupId(value);
        assertEquals("'getGroupId' should be correct.", value, impl.getGroupId());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupId(long groupId)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetGroupId() {
        long value = 42;
        impl.setGroupId(value);
        assertEquals("'setGroupId' should be correct.", value, TestHelper.getField(impl, "groupId"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroup()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroup() {
        Group value = new Group();
        impl.setGroup(value);
        assertEquals("'getGroup' should be correct.", value, impl.getGroup());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroup(Group group)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetGroup() {
        Group value = new Group();
        impl.setGroup(value);
        assertEquals("'setGroup' should be correct.", value, TestHelper.getField(impl, "group"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroupService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroupService() {
        GroupService value = EasyMock.createNiceMock(GroupService.class);
        impl.setGroupService(value);
        assertEquals("'getGroupService' should be correct.", value, impl.getGroupService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroupService(GroupService groupService)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetGroupService() {
        GroupService value = EasyMock.createNiceMock(GroupService.class);
        impl.setGroupService(value);
        assertEquals("'setGroupService' should be correct.", value, TestHelper.getField(impl, "groupService"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate() {
        UpdateGroupAction instance = createMockInstance();
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
        UpdateGroupAction instance = createMockInstance();
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
        UpdateGroupAction instance = createMockInstance();
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
        UpdateGroupAction instance = createMockInstance();
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
        UpdateGroupAction instance = createMockInstance();
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
        UpdateGroupAction instance = createMockInstance();
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
        UpdateGroupAction instance = createMockInstance();
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
        UpdateGroupAction instance = createMockInstance();
        Group group2 = new Group();
        fillField(group2);
        instance.setGroup(group2);
        GroupMember groupMember = new GroupMember();
        instance.getGroupMembers().add(groupMember);
        instance.validate();
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
    private UpdateGroupAction createMockInstance() {
        UpdateGroupAction instance = new UpdateGroupAction() {
            @Override
            public String getText(String textName) {
                return "mocked";
            }
        };

        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        instance.setGroupMembers(groupMembers);

        return instance;
    }
}
