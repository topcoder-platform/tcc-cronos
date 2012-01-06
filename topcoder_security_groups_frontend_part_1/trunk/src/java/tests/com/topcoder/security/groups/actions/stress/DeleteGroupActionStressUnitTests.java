/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stress;

import java.util.Date;

import org.junit.Test;

import com.topcoder.security.groups.actions.DeleteGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;

/**
 * <p>
 * Stress unit test case for {@link DeleteGroupAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class DeleteGroupActionStressUnitTests extends BaseStressUnitTests {
    /**
     * <p>
     * The {@link DeleteGroupAction#execute()}.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testExecute() throws Exception {
        Date start = new Date();
        for (int i = 0; i < 100000; i++) {
            Thread thread = new Thread() {
                public void run() {
                    DeleteGroupAction deleteGroupAction = (DeleteGroupAction) CONTEXT.getBean("deleteGroupAction");
                    try {
                        deleteGroupAction.execute();
                    } catch (SecurityGroupsActionException e) {
                        //ignore
                    }
                }
            };
            thread.start();
        }
        Date end = new Date();
        System.out.print("DeleteGroupAction#execute Run 100000 times in "
                + String.valueOf(end.getTime() - start.getTime()) + "ms");
    }
}
