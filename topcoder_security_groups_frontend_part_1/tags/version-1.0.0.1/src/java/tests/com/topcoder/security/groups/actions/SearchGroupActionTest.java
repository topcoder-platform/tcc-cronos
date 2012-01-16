/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * <p>
 * Unit tests for class <code>SearchGroupAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class SearchGroupActionTest {
    /**
     * <p>
     * Represents the <code>SearchGroupAction</code> instance used to test against.
     * </p>
     */
    private SearchGroupAction impl;

    /**
     * <p>
     * Represents the <code>GroupSearchCriteria</code> instance used to test against.
     * </p>
     */
    private GroupSearchCriteria criteria;

    /**
     * Purpose: pageSize is used to represents the page size. Usage: It's passed as the http input parameter for
     * this action. Legal Values: Positive after set
     */
    private int pageSize;

    /**
     * Purpose: page is used to represents the page. Usage: It's passed as the http input parameter for this
     * action. Legal Values: Positive after set
     */
    private int page;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SearchGroupActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new SearchGroupAction();
        criteria = new GroupSearchCriteria();
        pageSize = 10;
        page = 5;
        impl.setCriteria(criteria);
        impl.setPageSize(pageSize);
        impl.setPage(page);
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
     * Accuracy test for the method <code>execute()</code> .<br>
     * Expects no error occurs.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        PagedResult<Group> groups = new PagedResult<Group>();
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        groupService.search(criteria, pageSize, page);
        EasyMock.expectLastCall().andReturn(groups);

        EasyMock.replay(groupService);

        impl.setGroupService(groupService);

        assertEquals("The return value should be same as ", "success", impl.execute());
        assertEquals("Groups should be same", groups, impl.getGroups());
        EasyMock.verify(groupService);
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * SecurityGroupException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecute2() throws Exception {
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        groupService.search(criteria, pageSize, page);
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("for test"));
        EasyMock.replay(groupService);

        impl.setGroupService(groupService);

        impl.execute();
    }

    /**
     * <p>
     * Failure test for the method <code>execute()</code>.<br>
     * RuntimeException occurs.<br>
     * Expect SecurityGroupsActionException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = SecurityGroupsActionException.class)
    public void testExecute3() throws Exception {
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        groupService.search(criteria, pageSize, page);
        EasyMock.expectLastCall().andThrow(new RuntimeException("for test"));
        EasyMock.replay(groupService);

        impl.setGroupService(groupService);

        impl.execute();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SearchGroupAction()</code>. Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        impl = new SearchGroupAction();
        assertNotNull(impl);
        assertNull("'criteria' should be correct.", impl.getCriteria());
        assertEquals("'pageSize' should be correct.", 0, impl.getPageSize());
        assertEquals("'page' should be correct.", 0, impl.getPage());
        assertNull("'groups' should be correct.", impl.getGroups());
        assertNull("'groupService' should be correct.", impl.getGroupService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getCriteria()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetCriteria() {
        GroupSearchCriteria value = new GroupSearchCriteria();
        impl.setCriteria(value);
        assertEquals("'getCriteria' should be correct.", value, impl.getCriteria());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setCriteria(GroupSearchCriteria criteria)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetCriteria() {
        GroupSearchCriteria value = new GroupSearchCriteria();
        impl.setCriteria(value);
        assertEquals("'setCriteria' should be correct.", value, TestHelper.getField(impl, "criteria"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPageSize()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetPageSize() {
        int value = 42;
        impl.setPageSize(value);
        assertEquals("'getPageSize' should be correct.", value, impl.getPageSize());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPageSize(int pageSize)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetPageSize() {
        int value = 42;
        impl.setPageSize(value);
        assertEquals("'setPageSize' should be correct.", value, TestHelper.getField(impl, "pageSize"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getPage()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetPage() {
        int value = 42;
        impl.setPage(value);
        assertEquals("'getPage' should be correct.", value, impl.getPage());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setPage(int page)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetPage() {
        int value = 42;
        impl.setPage(value);
        assertEquals("'setPage' should be correct.", value, TestHelper.getField(impl, "page"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getGroups()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetGroups() {
        PagedResult<Group> value = new PagedResult<Group>();
        impl.setGroups(value);
        assertEquals("'getGroups' should be correct.", value, impl.getGroups());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setGroups(PagedResult<Group> groups)</code>. The value should be properly
     * set.
     * </p>
     */
    @Test
    public void testSetGroups() {
        PagedResult<Group> value = new PagedResult<Group>();
        impl.setGroups(value);
        assertEquals("'setGroups' should be correct.", value, TestHelper.getField(impl, "groups"));
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
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    public static void fillField(SearchGroupAction instance) {
        BaseActionTest.fillField(instance);
        instance.setGroupService(EasyMock.createNiceMock(GroupService.class));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckInit() {
        impl = new SearchGroupAction();
        fillField(impl);
        impl.checkInit();
    }

    /**
     * <p>
     * Failure test for the method <code>checkInit()</code> .<br>
     * groupService is null.<br>
     * Expect SecurityGroupsActionConfigurationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionConfigurationException.class)
    public void testCheckInit2() {
        impl = new SearchGroupAction();
        fillField(impl);
        impl.setGroupService(null);
        impl.checkInit();
    }

    /**
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    private void fillField(GroupSearchCriteria instance) {
        instance.setGroupName("groupName");
        instance.setClientName("clientName");
        instance.setProjectName("projectName");
        List<GroupPermissionType> permissions = new ArrayList<GroupPermissionType>();
        permissions.add(GroupPermissionType.FULL);
        instance.setPermissions(permissions);
        instance.setBillingAccountName("billingAccountName");
        instance.setGroupMemberHandle("groupMemberHandle");
    }

    /**
     * create a mock instance of SearchGroupAction.
     *
     * @return the SearchGroupAction instance
     */
    @SuppressWarnings("serial")
    private SearchGroupAction createMockInstance() {
        SearchGroupAction instance = new SearchGroupAction() {
            @Override
            public String getText(String textName) {
                return "mocked";
            }
        };
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
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        impl.setCriteria(criteria2);
        impl.validate();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * groupName is null.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate2() {
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        criteria2.setGroupName(null);
        impl.setCriteria(criteria2);
        impl.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * groupName's length is bigger than 45.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate3() {
        SearchGroupAction instance = createMockInstance();
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        char[] data = new char[46];
        Arrays.fill(data, 'a');
        criteria2.setGroupName(String.valueOf(data));
        instance.setCriteria(criteria2);
        instance.validate();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * clientName is null.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate4() {
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        criteria2.setClientName(null);
        impl.setCriteria(criteria2);
        impl.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * clientName's length is bigger than 45.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate5() {
        SearchGroupAction instance = createMockInstance();
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        char[] data = new char[46];
        Arrays.fill(data, 'a');
        criteria2.setClientName(String.valueOf(data));
        instance.setCriteria(criteria2);
        instance.validate();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * projectName is null.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate6() {
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        criteria2.setProjectName(null);
        impl.setCriteria(criteria2);
        impl.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * projectName's length is bigger than 45.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate7() {
        SearchGroupAction instance = createMockInstance();
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        char[] data = new char[46];
        Arrays.fill(data, 'a');
        criteria2.setProjectName(String.valueOf(data));
        instance.setCriteria(criteria2);
        instance.validate();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * permissions is null.<br>
     * Expect no error occurs.
     * </p>
     */
    @Test
    public void testValidate8() {
        SearchGroupAction instance = createMockInstance();
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        criteria2.setPermissions(null);
        instance.setCriteria(criteria2);
        instance.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * permissions's size is bigger than 1.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate9() {
        SearchGroupAction instance = createMockInstance();
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        criteria2.getPermissions().add(GroupPermissionType.READ);
        instance.setCriteria(criteria2);
        instance.validate();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * billingAccountName is null.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate10() {
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        criteria2.setBillingAccountName(null);
        impl.setCriteria(criteria2);
        impl.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * billingAccountName's length is bigger than 45.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate11() {
        SearchGroupAction instance = createMockInstance();
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        char[] data = new char[46];
        Arrays.fill(data, 'a');
        criteria2.setBillingAccountName(String.valueOf(data));
        instance.setCriteria(criteria2);
        instance.validate();
    }

    /**
     * <p>
     * Accuracy test for the method <code>validate()</code> .<br>
     * groupMemberHandle is null.<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testValidate12() {
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        criteria2.setGroupMemberHandle(null);
        impl.setCriteria(criteria2);
        impl.validate();
    }

    /**
     * <p>
     * Failure test for the method <code>validate()</code> .<br>
     * groupMemberHandle's length is bigger than 45.<br>
     * Expect SecurityGroupsActionValidationException.
     * </p>
     */
    @Test(expected = SecurityGroupsActionValidationException.class)
    public void testValidate13() {
        SearchGroupAction instance = createMockInstance();
        GroupSearchCriteria criteria2 = new GroupSearchCriteria();
        fillField(criteria2);
        char[] data = new char[46];
        Arrays.fill(data, 'a');
        criteria2.setGroupMemberHandle(String.valueOf(data));
        instance.setCriteria(criteria2);
        instance.validate();
    }

}
