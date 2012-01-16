/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stress;

import java.util.Date;

import org.junit.Test;

import com.topcoder.security.groups.actions.CreateGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;

/**
 * <p>
 * Stress unit test case for {@link CreateGroupAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class CreateGroupActionStressUnitTests extends BaseStressUnitTests {
    /**
     * <p>
     * The {@link CreateGroupAction#createGroup()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testCreateGroup() throws Exception {
        Date start = new Date();
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread() {
                public void run() {
                    CreateGroupAction createGroupAction = (CreateGroupAction) CONTEXT.getBean("createGroupAction");
                    createGroupAction.setSession(SESSION);
                    createGroupAction.setGroupSessionKey("groupSessionKey");
                    try {
                        createGroupAction.createGroup();
                    } catch (SecurityGroupsActionException e) {
                        //ignore
                    }
                }
            };
            thread.start();
        }
        Date end = new Date();
        System.out.print("CreateGroupAction#createGroup Run 100000 times in "
                + String.valueOf(end.getTime() - start.getTime()) + "ms");
    }
}
