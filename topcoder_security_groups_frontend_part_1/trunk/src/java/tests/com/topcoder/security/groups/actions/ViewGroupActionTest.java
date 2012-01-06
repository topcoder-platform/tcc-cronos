/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * <p>
 * Unit tests for class <code>ViewGroupAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class ViewGroupActionTest {
    /**
     * <p>
     * Represents the <code>ViewGroupAction</code> instance used to test against.
     * </p>
     */
    private ViewGroupAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ViewGroupActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new ViewGroupAction();
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
        impl.setGroupId(10);
        Group group = new Group();
        group.setId(1);
        group.setName("group name");
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        EasyMock.expect(groupService.get(10)).andReturn(group);
        EasyMock.replay(groupService);

        impl.setGroupService(groupService);

        assertEquals("The return value should be same as ", "success", impl.execute());

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
        impl.setGroupId(10);
        Group group = new Group();
        group.setId(1);
        group.setName("group name");
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        EasyMock.expect(groupService.get(10)).andThrow(new SecurityGroupException("for test"));
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
        impl.setGroupId(10);
        Group group = new Group();
        group.setId(1);
        group.setName("group name");
        GroupService groupService = EasyMock.createNiceMock(GroupService.class);
        EasyMock.expect(groupService.get(10)).andThrow(new RuntimeException("for test"));
        EasyMock.replay(groupService);

        impl.setGroupService(groupService);
        impl.execute();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>ViewGroupAction()</code>. Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        impl = new ViewGroupAction();
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
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    public static void fillField(ViewGroupAction instance) {
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
        impl = new ViewGroupAction();
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
        impl = new ViewGroupAction();
        fillField(impl);
        impl.setGroupService(null);
        impl.checkInit();
    }
}
