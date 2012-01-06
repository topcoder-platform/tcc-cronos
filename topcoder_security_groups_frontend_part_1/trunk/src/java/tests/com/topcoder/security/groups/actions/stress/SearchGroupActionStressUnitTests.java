/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.security.groups.actions.stress;

import java.util.Date;

import org.junit.Test;

import com.topcoder.security.groups.actions.SearchGroupAction;
import com.topcoder.security.groups.actions.SecurityGroupsActionException;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;

/**
 * <p>
 * Stress unit test case for {@link SearchGroupAction}.
 * </p>
 *
 * @author hesibo
 * @version 1.0
 */
public class SearchGroupActionStressUnitTests extends BaseStressUnitTests {
    /**
     * <p>
     * The {@link SearchGroupAction#execute()}.
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
                    SearchGroupAction searchGroupAction = (SearchGroupAction) CONTEXT.getBean("searchGroupAction");
                    try {
                        searchGroupAction.setCriteria(new GroupSearchCriteria());
                        searchGroupAction.execute();
                    } catch (SecurityGroupsActionException e) {
                        //ignore
                    }
                }
            };
            thread.start();
        }
        Date end = new Date();
        System.out.print("SearchGroupAction#execute Run 100000 times in "
                + String.valueOf(end.getTime() - start.getTime()) + "ms");
    }
}
