/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.SearchGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.security.groups.services.dto.PagedResult;

/**
 * Accuracy test for {@link SearchGroupAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class SearchGroupActionAccTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private SearchGroupAction instance;
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
        instance = new SearchGroupAction();
        groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
        GroupSearchCriteria criteria = new GroupSearchCriteria();
        instance.setCriteria(criteria);
        instance.setPage(1);
        instance.setPageSize(1);
    }
    /**
     * Accuracy test for {@link SearchGroupAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        PagedResult<Group> groups = new PagedResult<Group>();
        groupService.search(instance.getCriteria(), 1, 1);
        EasyMock.expectLastCall().andReturn(groups).times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.SUCCESS, instance.execute());
        assertEquals("The result should be correct.", groups, instance.getGroups());
        control.verify();
    }
    /**
     * Accuracy test for {@link SearchGroupAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy2() throws Exception {
        groupService.search(instance.getCriteria(), 1, 1);
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
     * Accuracy test for {@link SearchGroupAction#execute()}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy3() throws Exception {
        groupService.search(instance.getCriteria(), 1, 1);
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
