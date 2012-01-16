/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.actions.DeleteGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.services.EntityNotFoundException;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.SecurityGroupException;

/**
 * Accuracy test for {@link DeleteGroupAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class DeleteGroupActionAccTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private DeleteGroupAction instance;
    /**
     * Represents the {@link GroupAuditService} used for test.
     */
    private GroupAuditService auditService;
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
        instance = new DeleteGroupAction();
        groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
        auditService = control.createMock(GroupAuditService.class);
        instance.setAuditService(auditService);
    }
    /**
     * Accuracy test for {@link DeleteGroupAction}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        instance.setGroupId(1);
        groupService.get(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(new Group()).times(1);
        groupService.delete(1);
        EasyMock.expectLastCall().times(1);
        auditService.add((GroupAuditRecord) EasyMock.anyObject());
        EasyMock.expectLastCall().andReturn(1L).times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.SUCCESS, instance.execute());
        control.verify();
    }
    /**
     * Accuracy test for {@link DeleteGroupAction}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy2() throws Exception {
        instance.setGroupId(1);
        groupService.get(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(new Group()).times(1);
        auditService.add((GroupAuditRecord) EasyMock.anyObject());
        EasyMock.expectLastCall().andReturn(1L).times(1);
        groupService.delete(EasyMock.anyLong());
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
     * Accuracy test for {@link DeleteGroupAction}.
     * @throws Exception to JUnit.
     */
    public void testExecuteAccuracy3() throws Exception {
        instance.setGroupId(1);
        groupService.get(EasyMock.anyLong());
        EasyMock.expectLastCall().andReturn(new Group()).times(1);
        auditService.add((GroupAuditRecord) EasyMock.anyObject());
        EasyMock.expectLastCall().andReturn(1L).times(1);
        groupService.delete(EasyMock.anyLong());
        EasyMock.expectLastCall().andThrow(new EntityNotFoundException("test")).times(1);
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
