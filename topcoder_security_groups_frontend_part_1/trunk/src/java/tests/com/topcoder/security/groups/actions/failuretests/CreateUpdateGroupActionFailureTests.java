/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.CreateGroupAction;
import com.topcoder.security.groups.actions.CreateUpdateGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.BillingAccountService;
import com.topcoder.security.groups.services.ClientService;
import com.topcoder.security.groups.services.CustomerAdministratorService;
import com.topcoder.security.groups.services.DirectProjectService;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for CreateUpdateGroupAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class CreateUpdateGroupActionFailureTests extends TestCase {
    /**
     * <p>
     * The CreateUpdateGroupAction instance for testing.
     * </p>
     */
    private CreateUpdateGroupAction instance;

    /**
     * <p>
     * The DirectProjectService instance for testing.
     * </p>
     */
    private DirectProjectService directProjectService;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        instance = new CreateGroupAction();

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("key", "value");
        instance.setSession(session);
        instance.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));

        instance.setGroupSessionKey("groupSessionKey");
        instance.setClientService(EasyMock.createNiceMock(ClientService.class));
        instance.setBillingAccountService(EasyMock.createNiceMock(BillingAccountService.class));
        directProjectService = EasyMock.createNiceMock(DirectProjectService.class);
        instance.setDirectProjectService(directProjectService);
        instance.setGroupInvitationService(EasyMock.createNiceMock(GroupInvitationService.class));
        instance.setGroupService(EasyMock.createNiceMock(GroupService.class));
        instance.setCustomerAdministratorService(EasyMock.createNiceMock(CustomerAdministratorService.class));
        instance.setRegistrationUrl("registrationUrl");
        instance.setAcceptRejectUrlBase("acceptRejectUrlBase");
        instance.setGroupMemberService(EasyMock.createNiceMock(GroupMemberService.class));
        instance.setOldGroupMembersSessionKey("oldGroupMembersSessionKey");

        instance.setSelectedClientId(1L);
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(CreateUpdateGroupActionFailureTests.class);
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#input() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testInput_SecurityGroupsActionException() throws Exception {
        directProjectService.getProjectsByClientId(1);
        EasyMock.expectLastCall().andThrow(new EntityNotFoundException("error"));
        EasyMock.replay(directProjectService);
        try {
            instance.input();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#addMembers() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     */
    public void testAddMembers_SecurityGroupsActionException() {
        try {
            instance.addMembers();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#deleteMember() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     */
    public void testDeleteMember_SecurityGroupsActionException() {
        try {
            instance.deleteMember();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when groupSessionKey is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullgroupSessionKey() {
        instance.setGroupSessionKey(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when groupSessionKey is empty and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_EmptygroupSessionKey() {
        instance.setGroupSessionKey(" ");
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when registrationUrl is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullregistrationUrl() {
        instance.setRegistrationUrl(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when registrationUrl is empty and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_EmptyregistrationUrl() {
        instance.setRegistrationUrl(" ");
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when acceptRejectUrlBase is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullacceptRejectUrlBase() {
        instance.setAcceptRejectUrlBase(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when acceptRejectUrlBase is empty and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_EmptyacceptRejectUrlBase() {
        instance.setAcceptRejectUrlBase(" ");
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when oldGroupMembersSessionKey is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NulloldGroupMembersSessionKey() {
        instance.setOldGroupMembersSessionKey(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when oldGroupMembersSessionKey is empty and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_EmptyoldGroupMembersSessionKey() {
        instance.setOldGroupMembersSessionKey(" ");
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when clientService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullclientService() {
        instance.setClientService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when billingAccountService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullbillingAccountService() {
        instance.setBillingAccountService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when directProjectService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NulldirectProjectService() {
        instance.setDirectProjectService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when groupInvitationService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullgroupInvitationService() {
        instance.setGroupInvitationService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when groupService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullgroupService() {
        instance.setGroupService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when customerAdministratorService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullcustomerAdministratorService() {
        instance.setCustomerAdministratorService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests CreateUpdateGroupAction#checkInit() for failure.
     * It tests the case that when groupMemberService is null and expects SecurityGroupsActionConfigurationException.
     * </p>
     */
    public void testCheckInit_NullgroupMemberService() {
        instance.setGroupMemberService(null);
        try {
            instance.checkInit();
            fail("SecurityGroupsActionConfigurationException expected.");
        } catch (SecurityGroupsActionConfigurationException e) {
            //good
        }
    }
}