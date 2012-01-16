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
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupAuditRecord;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.GroupAuditService;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupService;

/**
 * Accuracy test for {@link CreateGroupAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class CreateGroupActionAccTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private CreateGroupAction instance;
    /**
     * Represents the {@link GroupService} used for test.
     */
    private GroupService groupService;
    /**
     * Represents the {@link GroupAuditService} used for test.
     */
    private GroupAuditService auditService;
    /**
     * Represents the {@link IMocksControl} instance used for test.
     */
    private IMocksControl control = EasyMock.createControl();
    /**
     * Represents the {@link GroupInvitationService} instance used for test.
     */
    private GroupInvitationService groupInvitationService;
    /**
     * Initializes the instance used for test.
     */
    public void setUp() {
        instance = new CreateGroupAction();
        groupInvitationService = control.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
        auditService = control.createMock(GroupAuditService.class);
        instance.setAuditService(auditService);
    }
    /**
     * Accuracy test for {@link CreateGroupAction#createGroup()}.
     * @throws Exception to JUnit.
     */
    public void testCreateGroupAccuracy() throws Exception {
        Group group = new Group();
        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        groupMembers.add(new GroupMember());
        group.setGroupMembers(groupMembers);
        instance.setGroup(group);

        auditService.add((GroupAuditRecord) EasyMock.anyObject());
        EasyMock.expectLastCall().andReturn(1L).times(1);
        groupService.add(group);
        EasyMock.expectLastCall().andReturn(1L).times(1);
        groupInvitationService.addInvitation((GroupInvitation) EasyMock.anyObject());
        EasyMock.expectLastCall().times(1);
        groupInvitationService.sendInvitation((GroupInvitation) EasyMock.anyObject(),
            (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject());
        EasyMock.expectLastCall().times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.SUCCESS, instance.createGroup());
        control.verify();
    }
}
