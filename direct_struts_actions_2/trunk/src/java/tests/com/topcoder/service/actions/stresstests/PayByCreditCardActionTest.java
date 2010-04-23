/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import org.easymock.EasyMock;
import org.junit.Test;

import com.opensymphony.xwork2.ActionProxy;
import com.topcoder.security.TCSubject;
import com.topcoder.service.actions.PayByCreditCardAction;
import com.topcoder.service.facade.contest.ContestPaymentResult;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.project.StudioCompetition;

/**
 * PayByCreditCardActionTest.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PayByCreditCardActionTest extends AbstractStressTest {

    /**
     * Test method for PayByCreditCardAction#execute().
     *
     * @throws Exception
     *             the exception occurs
     */
    @Test
    public void testExecute() throws Exception {
        // set mock
        StudioCompetition competition = new StudioCompetition();
        ContestPaymentResult result = new ContestPaymentResult();
        TCSubject tc = null;
        EasyMock.expect(contestServiceFacade.getContest((TCSubject) EasyMock.eq(tc), EasyMock.eq(1L)))
            .andReturn(competition).times(StressHelper.EXCUTIMES);
        EasyMock.expect(
            contestServiceFacade.processContestCreditCardPayment(EasyMock.eq(tc), EasyMock
                .isA(StudioCompetition.class), EasyMock.isA(CreditCardPaymentData.class))).andReturn(result)
            .times(StressHelper.EXCUTIMES);
        EasyMock.replay(contestServiceFacade);
        // test method
        stress.getStartTime();
        for (int i = 0; i < StressHelper.EXCUTIMES; i++) {
            ActionProxy proxy = getActionProxy("/payCredit.action");
            PayByCreditCardAction cate = setInstance((PayByCreditCardAction) proxy.getAction());
            cate.setContestServiceFacade(contestServiceFacade);
            cate.execute();
            assertEquals(0, cate.getFieldErrors().size());
        }
        stress.getEndTime();

        stress.printResultToFile("PayByCreditCardAction", "execute", "");
        // verify mock
        control.verify();

    }

    /**
     * Sets the instance.
     *
     * @param cate
     *            the cate instance
     * @return the pay by credit card action
     */
    private PayByCreditCardAction setInstance(PayByCreditCardAction cate) {
        cate.prepare();
        cate.setContestId(1L);
        cate.setCardExpiryMonth("12");
        cate.setCardExpiryYear("2020");
        cate.setCardType("Visa");
        cate.setCity("Omaha");
        cate.setCountry("US");
        cate.setCsc("123");
        cate.setState("NE");
        return cate;
    }

}
