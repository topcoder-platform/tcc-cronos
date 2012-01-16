/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import com.opensymphony.xwork2.Action;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupInvitation;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.GroupInvitationService;
import com.topcoder.security.groups.services.GroupMemberService;
import com.topcoder.security.groups.services.GroupService;

/**
 * Accuracy test for {@link UpdateGroupAction}.
 * @author BlackMagic
 * @version 1.0
 */
public class UpdateGroupActionAccTest extends TestCase {
    /**
     * Represents the instance used for test.
     */
    private UpdateGroupAction instance;
    /**
     * Represents the {@link GroupService} used for test.
     */
    private GroupService groupService;
    /**
     * Represents the {@link IMocksControl} instance used for test.
     */
    private IMocksControl control = EasyMock.createControl();
    /**
     * Represents the {@link GroupInvitationService} instance used for test.
     */
    private GroupInvitationService groupInvitationService;
    /**
     * Represents the {@link GroupMemberService} instance used for test.
     */
    private GroupMemberService groupMemberService;
    /**
     * Initializes the instance used for test.
     */
    public void setUp() {
        instance = new UpdateGroupAction();
        groupInvitationService = control.createMock(GroupInvitationService.class);
        instance.setGroupInvitationService(groupInvitationService);
        groupService = control.createMock(GroupService.class);
        instance.setGroupService(groupService);
        groupMemberService = control.createMock(GroupMemberService.class);
        instance.setGroupMemberService(groupMemberService);
        instance.setSession(new HashMap<String, Object>());
    }
    /**
     * Accuracy test for {@link UpdateGroupAction#updateGroup()}.
     * @throws Exception to JUnit.
     */
    public void testUpdateGroupAccuracy() throws Exception {
        Group group = new Group();
        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        groupMembers.add(new GroupMember());
        instance.setGroup(group);
        instance.setGroupSessionKey("group");
        instance.getSession().put("group", group);
        instance.setOldGroupMembersSessionKey("old");
        instance.setGroupMembers(groupMembers);
        instance.getSession().put("old", groupMembers);
        List<GroupMember> groupMembers2 = new ArrayList<GroupMember>(groupMembers);
        GroupMember groupMember = new GroupMember();
        groupMember.setId(1);
        groupMembers2.add(groupMember);
        group.setGroupMembers(groupMembers2);

        groupService.update(group);
        EasyMock.expectLastCall().times(1);
        groupMemberService.update((GroupMember) EasyMock.anyObject());
        EasyMock.expectLastCall().times(2);
        groupInvitationService.addInvitation((GroupInvitation) EasyMock.anyObject());
        EasyMock.expectLastCall().times(1);
        groupInvitationService.sendInvitation((GroupInvitation) EasyMock.anyObject(),
            (String) EasyMock.anyObject(), (String) EasyMock.anyObject(), (String) EasyMock.anyObject());
        EasyMock.expectLastCall().times(1);
        control.replay();
        assertEquals("The result should be correct.", Action.SUCCESS, instance.updateGroup());
        control.verify();
    }
    /**
     * Accuracy test for {@link UpdateGroupAction#enterGroupDetails()}.
     * @throws Exception to JUnit.
     */
    public void testEnterGroupDetailsAccuracy() throws Exception {
        instance.setGroupId(1);
        
        instance.setGroupSessionKey("test");
        instance.setOldGroupMembersSessionKey("old");
        Group group = new Group();
        List<GroupMember> groupMembers = new ArrayList<GroupMember>();
        group.setGroupMembers(groupMembers);
        groupService.get(1L);
        EasyMock.expectLastCall().andReturn(group).times(1);
        control.replay();
        instance.enterGroupDetails();
        assertEquals("The result should be correct.", group, instance.getSession().get("test"));
        assertEquals("The result should be correct.", groupMembers, instance.getSession().get("old"));
        control.verify();
    }
}
