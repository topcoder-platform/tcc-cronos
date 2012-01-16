/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.model.GroupMember;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;

/**
 * <p>
 * Demo tests.
 * </p>
 *
 * @author hanshuai
 * @version 1.0
 */
public class DemoTest {

    /**
     * <p>
     * Represents the <code>ClassPathXmlApplicationContext</code> instance used in test.
     * </p>
     */
    private ClassPathXmlApplicationContext applicationContext;

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DemoTest.class);
    }

    /**
     * <p>
     * Set up the test environment.
     * </p>
     */
    @Before
    public void setUp() {
        // Load the Spring context file
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    /**
     * <p>
     * The Demo API for UpdateAction and CreateAction.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testDemoAPI() throws Exception {
        // Mock the session
        Map<String, Object> session = new HashMap<String, Object>();
        String groupSessionKey = "groupSessionKey";
        Group group = new Group();
        group.setGroupMembers(new ArrayList<GroupMember>());
        session.put(groupSessionKey, group);

        // Get the updateGroupAction from Spring
        UpdateGroupAction updateGroupAction = (UpdateGroupAction) applicationContext.getBean("updateGroupAction");
        updateGroupAction.setSession(session);
        updateGroupAction.setGroupSessionKey(groupSessionKey);
        updateGroupAction.setGroupId(2);
        Group newGroup = new Group();
        newGroup.setName("newGroupName");
        updateGroupAction.setGroup(group);
        // Update the group
        updateGroupAction.updateGroup();

        // Get the createGroupAction from Spring
        CreateGroupAction createGroupAction = (CreateGroupAction) applicationContext.getBean("createGroupAction");

        createGroupAction.setGroup(group);
        createGroupAction.setSession(session);

        // Create the group
        createGroupAction.createGroup();

        // Get the input
        createGroupAction.input();

        // Enter group details
        createGroupAction.enterGroupDetails();

        List<GroupMember> members = new ArrayList<GroupMember>();
        GroupMember member1 = new GroupMember();
        members.add(member1);
        createGroupAction.setGroupMembers(members);
        // Add members to the group. members should be added to the group.
        createGroupAction.addMembers();
        assertEquals(group.getGroupMembers().size(), 1);

        // Add members to the group. member should be deleted from the group.
        createGroupAction.setGroupMember(member1);
        createGroupAction.deleteMember();
        assertEquals(group.getGroupMembers().size(), 0);
    }

    /**
     * <p>
     * The Demo API.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testDemoAPI2() throws Exception {
        // Get the deleteGroupAction from Spring
        DeleteGroupAction deleteGroupAction = (DeleteGroupAction) applicationContext.getBean("deleteGroupAction");
        // Delete the group
        deleteGroupAction.execute();

        // Get the searchGroupAction from Spring
        SearchGroupAction searchGroupAction = (SearchGroupAction) applicationContext.getBean("searchGroupAction");
        searchGroupAction.setCriteria(new GroupSearchCriteria());
        // Search the group
        searchGroupAction.execute();

        // Get the searchUserAction from Spring
        SearchUserAction searchUserAction = (SearchUserAction) applicationContext.getBean("searchUserAction");
        searchUserAction.setHandle("handle");
        // Search the user
        searchUserAction.execute();

        // Get the viewGroupAction from Spring
        ViewGroupAction viewGroupAction = (ViewGroupAction) applicationContext.getBean("viewGroupAction");
        viewGroupAction.setGroupId(10);
        // View the group
        viewGroupAction.execute();
    }
}
