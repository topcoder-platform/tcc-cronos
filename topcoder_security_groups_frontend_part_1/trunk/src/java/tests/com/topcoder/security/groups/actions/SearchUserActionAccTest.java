/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.SearchUserAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.UserService;
import com.topcoder.security.groups.services.dto.UserDTO;
/**
 * Accuracy test for {@link SearchUserAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class SearchUserActionAccTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private SearchUserAction instance;
    /**
     * Represents the {@link IMocksControl} instance used for test.
     */
    private IMocksControl control = EasyMock.createControl();
    /**
     * Represents the {@link UserService} used for test.
     */
    private UserService userService;
    /**
     * Initializes the instance used for test.
     */
    public void setUp() {
        instance = new SearchUserAction();
        userService = control.createMock(UserService.class);
        instance.setUserService(userService);
    }
    /**
     * Accuracy test for {@link SearchUserAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        instance.setHandle("test");
        userService.search("test");
        List<UserDTO> users = new ArrayList<UserDTO>();
        EasyMock.expectLastCall().andReturn(users).times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.SUCCESS, instance.execute());
        assertEquals("The result should be correct.", users, instance.getUsers());
        control.verify();
    }
    /**
     * Accuracy test for {@link SearchUserAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy2() throws Exception {
        instance.setHandle("test");
        userService.search("test");
        EasyMock.expectLastCall().andThrow(new SecurityGroupException("test")).times(1);
        control.replay();
        try {
            instance.execute();
            fail();
        } catch (SecurityGroupsActionException e) {
            //pass
        }
        control.verify();
    }
    /**
     * Accuracy test for {@link SearchUserAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy3() throws Exception {
        instance.setHandle("test");
        userService.search("test");
        EasyMock.expectLastCall().andThrow(new IllegalArgumentException("test")).times(1);
        control.replay();
        try {
            instance.execute();
            fail();
        } catch (SecurityGroupsActionException e) {
            //pass
        }
        control.verify();
    }
}
