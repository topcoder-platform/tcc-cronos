/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import org.junit.Test;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.actions.GetContestTechnologiesAction;

/**
 * GetContestTechnologiesActionTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GetContestTechnologiesActionTest extends AbstractStressTest {

    /**
     * Test GetContestTechnologiesAction#executeAction().
     *
     * @throws Exception
     *             the exception occurs
     */
    @Test
    public void testExecuteAction() throws Exception {

        stress.getStartTime();
        for (int i = 0; i < StressHelper.EXCUTIMES; i++) {
            ActionProxy proxy = getActionProxy("/technologies.action");
            GetContestTechnologiesAction cate = (GetContestTechnologiesAction) proxy.getAction();
            cate.setContestServiceFacade(contestServiceFacade);
            String result = proxy.execute();
            assertEquals(Action.SUCCESS, result);
        }
        stress.getEndTime();

        stress.printResultToFile("GetContestTechnologiesAction", "executeAction", "");
    }

}
