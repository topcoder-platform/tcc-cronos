/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import org.easymock.EasyMock;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.security.TCSubject;
import com.topcoder.service.actions.PayByBillingAccountAction;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.project.StudioCompetition;

/**
 * PayByBillingAccountActionTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PayByBillingAccountActionTest extends AbstractStressTest {

    /**
     * Test PayContestAction#executeAction().
     *
     * @throws Exception
     *             the exception occurs
     */
    @Test
    public void testExecuteAction() throws Exception {
        // set mock
        StudioCompetition competition = new StudioCompetition();
        ContestPaymentResult result = new ContestPaymentResult();
        TCSubject tc = null;
        EasyMock.expect(contestServiceFacade.getContest((TCSubject) EasyMock.eq(tc), EasyMock.eq(1L)))
            .andReturn(competition).times(StressHelper.EXCUTIMES);
        EasyMock.expect(
            contestServiceFacade.processContestPurchaseOrderPayment(EasyMock.eq(tc), EasyMock
                .isA(StudioCompetition.class), EasyMock.isA(TCPurhcaseOrderPaymentData.class))).andReturn(
            result).times(StressHelper.EXCUTIMES);
        EasyMock.replay(contestServiceFacade);
        // test method
        stress.getStartTime();
        for (int i = 0; i < StressHelper.EXCUTIMES; i++) {
            ActionProxy proxy = getActionProxy("/payBillingAccount.action");
            PayByBillingAccountAction cate = (PayByBillingAccountAction) proxy.getAction();
            cate.setContestServiceFacade(contestServiceFacade);
            cate.prepare();
            cate.setContestId(1);
            cate.execute();
            assertEquals(0, cate.getFieldErrors().size());
        }
        stress.getEndTime();

        stress.printResultToFile("PayByBillingAccountAction", "executeAction", "");
        // verify mock
        control.verify();
    }

}
