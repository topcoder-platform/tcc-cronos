/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.failuretests;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestNotFoundException;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestScheduleExtension;
import com.topcoder.service.facade.direct.ejb.DirectServiceFacadeBean;
import com.topcoder.service.facade.direct.ejb.FailureTestHelper;

/**
 * <p>
 * This test case aggregates failure test cases for DirectServiceFacadeBean.
 * </p>
 * @author vilain
 * @version 1.0
 */
public class DirectServiceFacadeBeanFailureTests {

    /**
     * Instance of DirectServiceFacadeBean for testing.
     */
    private DirectServiceFacadeBean directServiceFacadeBean;

    /**
     * Setting up environment.
     * @throws Exception wraps all exceptions
     */
    @Before
    public void setUp() throws Exception {
        directServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper.initializeDirectServiceFacadeBean(directServiceFacadeBean, true, true, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetContestReceiptDataFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.getContestReceiptData(null, 1, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetContestReceiptDataFailureContestIDZero() throws Exception {
        directServiceFacadeBean.getContestReceiptData(new TCSubject(999), 0, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest is not found.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = ContestNotFoundException.class)
    public final void testGetContestReceiptDataFailureContestNotFound() throws Exception {
        directServiceFacadeBean.getContestReceiptData(new TCSubject(998), 500, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestReceiptDataFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.getContestReceiptData(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case user service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestReceiptDataFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean.getContestReceiptData(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestReceiptDataFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean.getContestReceiptData(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSendContestReceiptByEmailFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.sendContestReceiptByEmail(null, 1, false, new String[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSendContestReceiptByEmailFailureContestIDZero() throws Exception {
        directServiceFacadeBean.sendContestReceiptByEmail(new TCSubject(999), 0, false, new String[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case additionalEmailAddrs contains
     * null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSendContestReceiptByEmailFailureAddrsContainsNull() throws Exception {
        directServiceFacadeBean.sendContestReceiptByEmail(new TCSubject(998), 999, false,
            new String[] {null });
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case additionalEmailAddrs contains
     * empty.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSendContestReceiptByEmailFailureAddrsContainsEmpty() throws Exception {
        directServiceFacadeBean.sendContestReceiptByEmail(new TCSubject(998), 999, false,
            new String[] {"     " });
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest is not found.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = ContestNotFoundException.class)
    public final void testSendContestReceiptByEmailFailureContestNotFound() throws Exception {
        directServiceFacadeBean.sendContestReceiptByEmail(new TCSubject(998), 500, true, new String[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testSendContestReceiptByEmailFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean
            .sendContestReceiptByEmail(new TCSubject(998), 999, true, new String[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case user service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testSendContestReceiptByEmailFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean
            .sendContestReceiptByEmail(new TCSubject(998), 999, true, new String[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#sendContestReceiptByEmail(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testSendContestReceiptByEmailFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean
            .sendContestReceiptByEmail(new TCSubject(998), 999, true, new String[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureTCSubjectNull() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(999);
        contestPrize.setContestPrizes(new double[] {500.0, 250.0 });
        directServiceFacadeBean.updateActiveContestPrize(null, contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contestPrize is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureContestPrizeNull() throws Exception {
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), null);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case id of contest is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureContestIdZero() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(0);
        contestPrize.setContestPrizes(new double[] {500.0, 250.0 });
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest prizes are null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureContestsPrizesNull() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(999);
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest prizes are empty.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureContestsPrizesEmpty() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(999);
        contestPrize.setContestPrizes(new double[0]);
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest prizes contain
     * negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureContestsPrizesContainNegative() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(999);
        contestPrize.setContestPrizes(new double[] {-1 });
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case milestone prizes contain
     * negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureMilestonePrizesContainNegative() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(999);
        contestPrize.setContestPrizes(new double[] {500.0 });
        contestPrize.setStudio(true);
        contestPrize.setMilestonePrizes(new double[] {-1 });
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case second prize is too low.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateContestPrizeFailureLowSecondPrize() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(999);
        contestPrize.setContestPrizes(new double[] {500.0, 90.0 });
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id of contest prize is
     * not found.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = ContestNotFoundException.class)
    public final void testUpdateContestPrizeFailureContestIDNotFound() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(500);
        contestPrize.setStudio(true);
        contestPrize.setContestPrizes(new double[] {500.0, 250.0 });
        directServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateActiveContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testUpdateActiveContestPrizeFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestId(999);
        contestPrize.setContestPrizes(new double[] {500.0, 250.0 });
        currentDirectServiceFacadeBean.updateActiveContestPrize(new TCSubject(998), contestPrize);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetContestPrizeFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.getContestPrize(null, 1, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetContestPrizeFailureContestIDZero() throws Exception {
        directServiceFacadeBean.getContestPrize(new TCSubject(999), 0, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest is not found.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = ContestNotFoundException.class)
    public final void testGetContestPrizeFailureContestNotFound() throws Exception {
        directServiceFacadeBean.getContestPrize(new TCSubject(998), 500, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestPrizeFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.getContestPrize(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestPrize(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case user service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestPrizeFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean.getContestPrize(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestPrize(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestPrizeFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean.getContestPrize(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetContestScheduleFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.getContestSchedule(null, 1, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetContestScheduleFailureContestIDZero() throws Exception {
        directServiceFacadeBean.getContestSchedule(new TCSubject(999), 0, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest is not found.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = ContestNotFoundException.class)
    public final void testGetContestScheduleFailureContestNotFound() throws Exception {
        directServiceFacadeBean.getContestSchedule(new TCSubject(998), 500, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestScheduleFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.getContestSchedule(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case user service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestScheduleFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean.getContestSchedule(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestSchedule(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetContestScheduleFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean.getContestSchedule(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testExtendActiveContestScheduleFailureTCSubjectNull() throws Exception {
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(999);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(36);
        directServiceFacadeBean.extendActiveContestSchedule(null, contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case extend registration is
     * negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testExtendActiveContestScheduleFailureExtendRegistrationNegative() throws Exception {
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(999);
        contestScheduleExtension.setExtendRegistrationBy(-1);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(36);
        directServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998), contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case extend milestone is negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testExtendActiveContestScheduleFailureExtendMilestoneNegative() throws Exception {
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(999);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(-1);
        contestScheduleExtension.setExtendSubmissionBy(36);
        directServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998), contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id of extend submission
     * is negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testExtendActiveContestScheduleFailureExtendSubmissionNegative() throws Exception {
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(999);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(-1);
        directServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998), contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case extend submission is negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testExtendActiveContestScheduleFailureContestIDZero() throws Exception {
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(0);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(36);
        directServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998), contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case extend submission id is not
     * found.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = ContestNotFoundException.class)
    public final void testExtendActiveContestScheduleFailureContestIDNotFound() throws Exception {
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(500);
        contestScheduleExtension.setStudio(true);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(36);
        directServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998), contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testExtendActiveContestScheduleFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(999);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(36);
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998),
            contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case user service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testExtendActiveContestScheduleFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(999);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(36);
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998),
            contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#extendActiveContestSchedule(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testExtendActiveContestScheduleFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        ContestScheduleExtension contestScheduleExtension = new ContestScheduleExtension();
        contestScheduleExtension.setContestId(999);
        contestScheduleExtension.setExtendRegistrationBy(12);
        contestScheduleExtension.setExtendMilestoneBy(24);
        contestScheduleExtension.setExtendSubmissionBy(36);
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean.extendActiveContestSchedule(new TCSubject(998),
            contestScheduleExtension);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#repostSoftwareContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testRepostSoftwareContestFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.repostSoftwareContest(null, 1);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#repostSoftwareContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testRepostSoftwareContestFailureContestIDZero() throws Exception {
        directServiceFacadeBean.repostSoftwareContest(new TCSubject(999), 0);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#repostSoftwareContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testRepostSoftwareContestFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.repostSoftwareContest(new TCSubject(998), 999);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#repostSoftwareContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case user service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testRepostSoftwareContestFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean.repostSoftwareContest(new TCSubject(998), 999);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testRepostSoftwareContestFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean.repostSoftwareContest(new TCSubject(998), 999);
    }

    /**
     * Method under test. Failure testing of exception in case tcSubject is
     * null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testCreateNewVersionForDesignDevContestFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.createNewVersionForDesignDevContest(null, 1);
    }

    /**
     * Method under test. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testCreateNewVersionForDesignDevContestFailureContestIDZero() throws Exception {
        directServiceFacadeBean.createNewVersionForDesignDevContest(new TCSubject(999), 0);
    }

    /**
     * Method under test. Failure testing of exception in case contest service
     * is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testCreateNewVersionForDesignDevContestFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.createNewVersionForDesignDevContest(new TCSubject(998), 999);
    }

    /**
     * Method under test. Failure testing of exception in case user service is
     * not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testCreateNewVersionForDesignDevContestFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean.createNewVersionForDesignDevContest(new TCSubject(998), 999);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getContestReceiptData(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testCreateNewVersionForDesignDevContestFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean.createNewVersionForDesignDevContest(new TCSubject(998), 999);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#deleteContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testDeleteContestFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.deleteContest(null, 1, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#deleteContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testDeleteContestFailureContestIDZero() throws Exception {
        directServiceFacadeBean.deleteContest(new TCSubject(999), 0, false);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#deleteContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest is not found.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = ContestNotFoundException.class)
    public final void testDeleteContestFailureContestNotFound() throws Exception {
        directServiceFacadeBean.deleteContest(new TCSubject(998), 500, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#deleteContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testDeleteContestFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.deleteContest(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#deleteContest(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case user service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testDeleteContestFailureUserServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, false, true);
        currentDirectServiceFacadeBean.deleteContest(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#deleteContest(com.topcoder.security.TCSubject, long, boolean)}
     * . Failure testing of exception in case project DAO is not initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testDeleteContestFailureProjectDAOMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, true, true, false);
        currentDirectServiceFacadeBean.deleteContest(new TCSubject(998), 999, true);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getProjectGamePlan(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetProjectGamePlanFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.getProjectGamePlan(null, 1);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getProjectGamePlan(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetProjectGamePlanFailureContestIDZero() throws Exception {
        directServiceFacadeBean.getProjectGamePlan(new TCSubject(999), 0);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getProjectGamePlan(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest service is not
     * initialized.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetProjectGamePlanFailureContestServiceMissed() throws Exception {
        DirectServiceFacadeBean currentDirectServiceFacadeBean = new DirectServiceFacadeBean();
        FailureTestHelper
            .initializeDirectServiceFacadeBean(currentDirectServiceFacadeBean, false, true, true);
        currentDirectServiceFacadeBean.getProjectGamePlan(new TCSubject(998), 999);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getChildProjects(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetChildProjectsFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.getChildProjects(null, 1);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getChildProjects(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetChildProjectsFailureContestIDZero() throws Exception {
        directServiceFacadeBean.getChildProjects(new TCSubject(999), 0);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getChildProjects(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case project link manager is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetChildProjectsFailureState() throws Exception {
        FailureTestHelper.setFieldValue(directServiceFacadeBean, "projectLinkManager", null);
        directServiceFacadeBean.getChildProjects(new TCSubject(998), 999);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectLinks(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateProjectLinksFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.updateProjectLinks(null, 999, new long[0], new long[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectLinks(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case project id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateProjectLinksFailureProjectIDZero() throws Exception {
        directServiceFacadeBean.updateProjectLinks(new TCSubject(999), 0, new long[0], new long[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectLinks(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case parent project ids contains
     * negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateProjectLinksFailureParentProjectIDsContainsNegative() throws Exception {
        directServiceFacadeBean.updateProjectLinks(new TCSubject(998), 999, new long[] {-1 }, new long[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectLinks(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case child project ids contains
     * negative.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateProjectLinksFailureChildProjectIDsContainsNegative() throws Exception {
        directServiceFacadeBean.updateProjectLinks(new TCSubject(998), 999, new long[0], new long[] {-1 });
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectLinks(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case project link manager is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testUpdateProjectLinksFailureState() throws Exception {
        FailureTestHelper.setFieldValue(directServiceFacadeBean, "projectLinkManager", null);
        directServiceFacadeBean.updateProjectLinks(new TCSubject(998), 999, new long[0], new long[0]);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getProjectBudget(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetProjectBudgetFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.getProjectBudget(null, 1);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getProjectBudget(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetProjectBudgetFailureContestIDZero() throws Exception {
        directServiceFacadeBean.getProjectBudget(new TCSubject(999), 0);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getProjectBudget(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case project DAO is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetProjectBudgetFailureState() throws Exception {
        FailureTestHelper.setFieldValue(directServiceFacadeBean, "projectDAO", null);
        directServiceFacadeBean.getProjectBudget(new TCSubject(998), 999);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectBudget(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateProjectBudgetFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.updateProjectBudget(null, 1, 1000);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectBudget(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUpdateProjectBudgetFailureContestIDZero() throws Exception {
        directServiceFacadeBean.updateProjectBudget(new TCSubject(999), 0, 1000);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#updateProjectBudget(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case project DAO is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testUpdateProjectBudgetFailureState() throws Exception {
        FailureTestHelper.setFieldValue(directServiceFacadeBean, "projectDAO", null);
        directServiceFacadeBean.updateProjectBudget(new TCSubject(998), 999, 1000);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getSpecReviewState(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case tcSubject is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetSpecReviewStateFailureTCSubjectNull() throws Exception {
        directServiceFacadeBean.getSpecReviewState(null, 1);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getSpecReviewState(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case contest id is 0.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testGetSpecReviewStateFailureContestIDZero() throws Exception {
        directServiceFacadeBean.getSpecReviewState(new TCSubject(999), 0);
    }

    /**
     * Method under test
     * {@link DirectServiceFacadeBean#getSpecReviewState(com.topcoder.security.TCSubject, long, boolean)
     * ()}. Failure testing of exception in case project manager is null.
     * @throws Exception wraps all exceptions
     */
    @Test(expected = IllegalStateException.class)
    public final void testGetSpecReviewStateFailureState() throws Exception {
        FailureTestHelper.setFieldValue(directServiceFacadeBean, "projectManager", null);
        directServiceFacadeBean.getSpecReviewState(new TCSubject(998), 999);
    }
}