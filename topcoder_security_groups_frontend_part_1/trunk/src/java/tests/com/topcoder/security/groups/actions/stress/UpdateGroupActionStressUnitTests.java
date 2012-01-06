/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stress;

import java.util.Date;

import org.junit.Test;

import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.actions.UpdateGroupAction;

/**
 * <p>
 * Stress unit test case for {@link UpdateGroupAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class UpdateGroupActionStressUnitTests extends BaseStressUnitTests {
    /**
     * <p>
     * The {@link UpdateGroupAction#updateGroup()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testUpdateGroup() throws Exception {
        Date start = new Date();
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread() {
                public void run() {
                    UpdateGroupAction updateGroupAction = (UpdateGroupAction) CONTEXT.getBean("updateGroupAction");
                    updateGroupAction.setSession(SESSION);
                    updateGroupAction.setGroupSessionKey("groupSessionKey");
                    try {
                        updateGroupAction.updateGroup();
                    } catch (SecurityGroupsActionException e) {
                        //ignore
                    }
                }
            };
            thread.start();
        }
        Date end = new Date();
        System.out.print("UpdateGroupAction#updateGroup Run 100000 times in "
                + String.valueOf(end.getTime() - start.getTime()) + "ms");
    }
}
