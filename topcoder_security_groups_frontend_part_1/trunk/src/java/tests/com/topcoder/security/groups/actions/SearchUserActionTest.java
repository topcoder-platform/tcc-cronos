/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;

/**
 * <p>
 * Unit tests for class <code>SearchUserAction</code>.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class SearchUserActionTest {
    /**
     * <p>
     * Represents the <code>SearchUserAction</code> instance used to test against.
     * </p>
     */
    private SearchUserAction impl;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(SearchUserActionTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        impl = new SearchUserAction();
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
     * fill the required field of the instance.
     *
     * @param instance
     *            the instance
     */
    public static void fillField(SearchUserAction instance) {
        BaseActionTest.fillField(instance);
        instance.setUserService(EasyMock.createNiceMock(UserService.class));
    }

    /**
     * <p>
     * Accuracy test for the method <code>checkInit()</code> .<br>
     * Expects no error occurs.
     * </p>
     */
    @Test
    public void testCheckInit() {
        impl = new SearchUserAction();
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
        impl = new SearchUserAction();
        fillField(impl);
        impl.setUserService(null);
        impl.checkInit();
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
        impl.setHandle("handle");
        List<UserDTO> users = new ArrayList<UserDTO>();
        UserService userService = EasyMock.createNiceMock(UserService.class);
        userService.search("handle");
        EasyMock.expectLastCall().andReturn(users);

        EasyMock.replay(userService);

        impl.setUserService(userService);

        assertEquals("The return value should be same as ", "success", impl.execute());
        assertEquals("users should be same", users, impl.getUsers());
        EasyMock.verify(userService);
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
        impl.setHandle("handle");
        UserService userService = EasyMock.createNiceMock(UserService.class);
        userService.search("handle");
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("for test"));

        EasyMock.replay(userService);

        impl.setUserService(userService);
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
        impl.setHandle("handle");
        UserService userService = EasyMock.createNiceMock(UserService.class);
        userService.search("handle");
        EasyMock.expectLastCall().andThrow(new RuntimeException("for test"));

        EasyMock.replay(userService);

        impl.setUserService(userService);
        impl.execute();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>SearchUserAction()</code>. Instance should be correctly created.
     * </p>
     */
    @Test
    public void testCtor() {
        assertNotNull(impl);
        assertNull("'handle' should be correct.", impl.getHandle());
        assertNull("'users' should be correct.", impl.getUsers());
        assertNull("'userService' should be correct.", impl.getUserService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>getHandle()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetHandle() {
        String value = "new_string";
        impl.setHandle(value);
        assertEquals("'getHandle' should be correct.", value, impl.getHandle());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setHandle(String handle)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetHandle() {
        String value = "new_string";
        impl.setHandle(value);
        assertEquals("'setHandle' should be correct.", value, TestHelper.getField(impl, "handle"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUsers()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetUsers() {
        List<UserDTO> value = new ArrayList<UserDTO>();
        value.add(new UserDTO());
        value.add(new UserDTO());
        impl.setUsers(value);
        assertEquals("'getUsers' should be correct.", value, impl.getUsers());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUsers(List<UserDTO> users)</code>. The value should be properly set.
     * </p>
     */
    @Test
    public void testSetUsers() {
        List<UserDTO> value = new ArrayList<UserDTO>();
        value.add(new UserDTO());
        value.add(new UserDTO());
        impl.setUsers(value);
        assertEquals("'setUsers' should be correct.", value, TestHelper.getField(impl, "users"));
    }

    /**
     * <p>
     * Accuracy test for the method <code>getUserService()</code>. The value should be properly retrieved.
     * </p>
     */
    @Test
    public void testGetUserService() {
        UserService value = EasyMock.createNiceMock(UserService.class);
        impl.setUserService(value);
        assertEquals("'getUserService' should be correct.", value, impl.getUserService());
    }

    /**
     * <p>
     * Accuracy test for the method <code>setUserService(UserService userService)</code>. The value should be
     * properly set.
     * </p>
     */
    @Test
    public void testSetUserService() {
        UserService value = EasyMock.createNiceMock(UserService.class);
        impl.setUserService(value);
        assertEquals("'setUserService' should be correct.", value, TestHelper.getField(impl, "userService"));
    }
}
