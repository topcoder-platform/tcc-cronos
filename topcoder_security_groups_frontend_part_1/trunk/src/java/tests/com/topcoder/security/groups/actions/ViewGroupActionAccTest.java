/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.ViewGroupAction;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * Accuracy test for {@link ViewGroupAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class ViewGroupActionAccTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private ViewGroupAction instance;
    /**
     * Represents the {@link IMocksControl} instance used for test.
     */
    private IMocksControl control = EasyMock.createControl();
    /**
     * Represents the {@link GroupService} used for test.
     */
    private GroupService groupService;
    /**
     * Initializes the instance used for test.
     */
    public void setUp() {
        instance = new ViewGroupAction();
        groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
    }
    /**
     * Accuracy test for {@link ViewGroupAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        instance.setGroupId(1);
        groupService.get(1);
        Group group = new Group();
        EasyMock.expectLastCall().andReturn(group).times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.SUCCESS, instance.execute());
        assertEquals("The result should be correct.", group, instance.getGroup());
        control.verify();
    }
    /**
     * Accuracy test for {@link ViewGroupAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy2() throws Exception {
        instance.setGroupId(1);
        groupService.get(1);
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
}
