/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.service.actions.DeleteDocumentContestAction;

/**
 * DeleteDocumentContestActionTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DeleteDocumentContestActionTest extends AbstractStressTest {

    /**
     * Test execute action.
     *
     * @throws Exception
     *             the exception occurs
     */
    @Test
    public void testExecuteAction() throws Exception {

        stress.getStartTime();
        for (int i = 0; i < StressHelper.EXCUTIMES; i++) {
            request.setParameter("documentId", "1");
            request.setParameter("contestId", "1");

            ActionProxy proxy = getActionProxy("/delete.action");
            DeleteDocumentContestAction cate = (DeleteDocumentContestAction) proxy.getAction();
            cate.setContestServiceFacade(contestServiceFacade);
            proxy.execute();
            assertEquals(0, cate.getFieldErrors().size());
        }
        stress.getEndTime();

        stress.printResultToFile("DeleteDocumentContestAction", "executeAction", "");
    }

}
