/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.SecurityGroupsActionValidationException;
import com.topcoder.security.groups.actions.UpdateGroupAction;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.Client;
import com.topcoder.security.groups.model.DirectProject;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for UpdateGroupAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class UpdateGroupActionFailureTests extends TestCase {
    /**
     * <p>
     * The UpdateGroupAction instance for testing.
     * </p>
     */
    private UpdateGroupAction instance;

    /**
     * <p>
     * The GroupService instance for testing.
     * </p>
     */
    private GroupService groupService;

    /**
     * <p>
     * The Group instance for testing.
     * </p>
     */
    private Group group;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    @SuppressWarnings("serial")
    protected void setUp() {
        instance = new UpdateGroupAction() {
            public String getText(String textName) {
                return "text";
            }
        };

        groupService = EasyMock.createNiceMock(GroupService.class);
        instance.setGroupService(groupService);

        group = new Group();
        group.setName("groupName");
        group.setDefaultPermission(GroupPermissionType.READ);

        Client client = new Client();
        client.setName("client");
        group.setClient(client);

        group.setBillingAccounts(Arrays.asList(new BillingAccount()));
        group.setDirectProjects(Arrays.asList(new DirectProject()));
        group.setRestrictions(Arrays.asList(ResourceType.PROJECT));

        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        GroupMember member = new GroupMember();
        member.setId(1);
        member.setSpecificPermission(GroupPermissionType.FULL);
        groupMembers.add(member);

        instance.setGroup(group);
        instance.setGroupMembers(groupMembers);
        instance.setGroupId(1);

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("groupSessionKey", group);
        instance.setSession(session);
        instance.setGroupSessionKey("groupSessionKey");
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(UpdateGroupActionFailureTests.class);
    }

    /**
     * <p>
     * Tests UpdateGroupAction#enterGroupDetails() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testEnterGroupDetails_SecurityGroupsActionException() throws Exception {
        groupService.get(1);
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("error"));
        EasyMock.replay(groupService);
        try {
            instance.enterGroupDetails();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests UpdateGroupAction#updateGroup() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testUpdateGroup_SecurityGroupsActionException() throws Exception {
        groupService.update(group);
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("error"));
        EasyMock.replay(groupService);
        try {
            instance.updateGroup();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.name is null and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_NullGroupName() throws Exception {
        group.setName(null);
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.name is empty and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_EmptyGroupName() throws Exception {
        group.setName(" ");
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.name is max 45 chars and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_InvalidGroupName() throws Exception {
        group.setName(FailureTestHelper.getText());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.client.name is null and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_NullGroupClientName() throws Exception {
        group.getClient().setName(null);
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.client.name is empty and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_EmptyGroupClientName() throws Exception {
        group.getClient().setName(" ");
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.client.name is max 45 chars and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_InvalidGroupClientName() throws Exception {
        group.getClient().setName(FailureTestHelper.getText());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.defaultPermission is null and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_NullGroupDefaultPermission() throws Exception {
        group.setDefaultPermission(null);
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.billingAccounts is null and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_NullGroupbillingAccounts() throws Exception {
        group.setBillingAccounts(null);
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.billingAccounts is empty and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_EmptyGroupbillingAccounts() throws Exception {
        group.setBillingAccounts(new ArrayList<BillingAccount>());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.directProjects is null and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_NullGroupdirectProjects() throws Exception {
        group.setDirectProjects(null);
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.directProjects is empty and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_EmptyGroupdirectProjects() throws Exception {
        group.setDirectProjects(new ArrayList<DirectProject>());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.restrictions is null and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_NullGrouprestrictions() throws Exception {
        group.setRestrictions(null);
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateGroupAction#validate() for failure.
     * It tests the case when group.restrictions is empty and expects SecurityGroupsActionValidationException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testValidate_EmptyGrouprestrictions() throws Exception {
        group.setRestrictions(new ArrayList<ResourceType>());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

}