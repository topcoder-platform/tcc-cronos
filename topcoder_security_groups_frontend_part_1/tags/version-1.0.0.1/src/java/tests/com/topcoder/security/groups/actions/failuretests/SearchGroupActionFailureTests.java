/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.failuretests;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;

import com.topcoder.security.groups.actions.SearchGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionConfigurationException;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.SecurityGroupsActionValidationException;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;

import junit.framework.TestSuite;
import junit.framework.Test;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test cases for SearchGroupAction.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class SearchGroupActionFailureTests extends TestCase {
    /**
     * <p>
     * The SearchGroupAction instance for testing.
     * </p>
     */
    private SearchGroupAction instance;

    /**
     * <p>
     * The GroupService instance for testing.
     * </p>
     */
    private GroupService groupService;

    /**
     * <p>
     * The GroupSearchCriteria instance for testing.
     * </p>
     */
    private GroupSearchCriteria criteria;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    @SuppressWarnings("serial")
    protected void setUp() {
        instance = new SearchGroupAction() {
            public String getText(String textName) {
                return "text";
            }
        };
        groupService = EasyMock.createNiceMock(GroupService.class);
        instance.setGroupService(groupService);
        instance.setAuditService(EasyMock.createNiceMock(GroupAuditService.class));
        instance.setAuthorizationService(EasyMock.createNiceMock(AuthorizationService.class));

        criteria = new GroupSearchCriteria();
        criteria.setGroupName("groupName");
        criteria.setClientName("clientName");
        criteria.setProjectName("projectName");
        List<GroupPermissionType> permissions = new ArrayList<GroupPermissionType>();
        permissions.add(GroupPermissionType.FULL);
        criteria.setPermissions(permissions);
        criteria.setBillingAccountName("billingAccountName");
        criteria.setGroupMemberHandle("groupMemberHandle");

        instance.setCriteria(criteria);
        instance.setPageSize(8);
        instance.setPage(10);

    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(SearchGroupActionFailureTests.class);
    }

    /**
     * <p>
     * Tests SearchGroupAction#checkInit() for failure.
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
     * Tests SearchGroupAction#execute() for failure.
     * Expects SecurityGroupsActionException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testExecute_SecurityGroupsActionException() throws Exception {
        groupService.search(criteria, 8, 10);
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("error"));
        EasyMock.replay(groupService);
        try {
            instance.execute();
            fail("SecurityGroupsActionException expected.");
        } catch (SecurityGroupsActionException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SearchGroupAction#validate() for failure.
     * It tests the case that when groupName is max 45 chars and expects SecurityGroupsActionValidationException.
     * </p>
     */
    public void testValidate_InvalidgroupName() {
        criteria.setGroupName(FailureTestHelper.getText());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SearchGroupAction#validate() for failure.
     * It tests the case that when clientName is max 45 chars and expects SecurityGroupsActionValidationException.
     * </p>
     */
    public void testValidate_InvalidclientName() {
        criteria.setClientName(FailureTestHelper.getText());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SearchGroupAction#validate() for failure.
     * It tests the case that when projectName is max 45 chars and expects SecurityGroupsActionValidationException.
     * </p>
     */
    public void testValidate_InvalidprojectName() {
        criteria.setProjectName(FailureTestHelper.getText());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SearchGroupAction#validate() for failure.
     * It tests the case that when billingAccountName is max 45 chars and expects SecurityGroupsActionValidationException.
     * </p>
     */
    public void testValidate_InvalidbillingAccountName() {
        criteria.setBillingAccountName(FailureTestHelper.getText());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SearchGroupAction#validate() for failure.
     * It tests the case that when groupMemberHandle is max 45 chars and expects SecurityGroupsActionValidationException.
     * </p>
     */
    public void testValidate_InvalidgroupMemberHandle() {
        criteria.setGroupMemberHandle(FailureTestHelper.getText());
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

    /**
     * <p>
     * Tests SearchGroupAction#validate() for failure.
     * It tests the case that when permission is invalid chars and expects SecurityGroupsActionValidationException.
     * </p>
     */
    public void testValidate_Invalidpermissions() {
        criteria.getPermissions().add(GroupPermissionType.READ);
        try {
            instance.validate();
            fail("SecurityGroupsActionValidationException expected.");
        } catch (SecurityGroupsActionValidationException e) {
            //good
        }
    }

}