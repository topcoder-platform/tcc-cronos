/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.direct.ejb;

import java.util.List;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.direct.ContestPlan;
import com.topcoder.service.facade.direct.ContestPrize;
import com.topcoder.service.facade.direct.ContestReceiptData;
import com.topcoder.service.facade.direct.ContestSchedule;
import com.topcoder.service.facade.direct.ContestScheduleExtension;
import com.topcoder.service.facade.direct.DirectServiceFacadeConfigurationException;
import com.topcoder.service.facade.direct.DirectServiceFacadeException;
import com.topcoder.service.facade.direct.ProjectBudget;
import com.topcoder.service.facade.direct.SpecReviewState;
import com.topcoder.service.facade.direct.SpecReviewStatus;

import junit.framework.TestCase;

/**
 * <p>
 * Unit tests for the <code>DirectServiceFacadeBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DirectServiceFacadeBeanTest extends TestCase {

    /**
     * <p>
     * Represents the <code>DirectServiceFacadeBean</code> instance for test.
     * </p>
     */
    private DirectServiceFacadeBean bean;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        bean = TestHelper.geDirectServiceFacadeBean();
    }

    /**
     * <p>
     * Accuracy test for the constructor <code>DirectServiceFacadeBean</code>, expects the instance is created
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCtorAccuracy() throws Exception {
        assertNotNull("Failed to create the DirectServiceFacadeBean instance.", bean);
    }

    /**
     * <p>
     * Accuracy test for the <code>initialize</code> method, expects the bean is initialized properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeAccuracy() throws Exception {
        bean.initialize();

        assertNotNull("Expects the log field is initialized properly.", TestHelper.getPrivateField(bean, "log"));
        assertTrue("Expects the projectManager field is initialized properly.", TestHelper.getPrivateField(bean,
                "projectManager") instanceof MockProjectManager);
        assertTrue("Expects the projectLinkManager field is initialized properly.", TestHelper.getPrivateField(bean,
                "projectLinkManager") instanceof MockProjectLinkManager);
        assertTrue("Expects the phaseManager field is initialized properly.", TestHelper.getPrivateField(bean,
                "phaseManager") instanceof MockPhaseManager);

        assertTrue("Expects the contestServiceFacade field is initialized properly.", TestHelper.getPrivateField(
                bean, "contestServiceFacade") instanceof MockContestServiceFacade);
        assertTrue("Expects the userService field is initialized properly.", TestHelper.getPrivateField(bean,
                "userService") instanceof MockUserService);
        assertTrue("Expects the projectDAO field is initialized properly.", TestHelper.getPrivateField(bean,
                "projectDAO") instanceof MockProjectDAO);

        assertEquals("Expects the receiptEmailTitleTemplateText field is initialized properly.",
                "Receipt for contest \"%contestName%\"", TestHelper.getPrivateField(bean,
                        "receiptEmailTitleTemplateText"));
        assertEquals("Expects the budgetUpdateEmailTitleTemplateText field is initialized properly.",
                "Budget Updated", TestHelper.getPrivateField(bean, "budgetUpdateEmailTitleTemplateText"));
        assertEquals("Expects the emailSender field is initialized properly.", "test@mydomain.com", TestHelper
                .getPrivateField(bean, "emailSender"));
    }

    /**
     * <p>
     * Failure test for the <code>initialize</code> method with the required parameter is null,
     * DirectServiceFacadeConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeWithRequiredParamNull() throws Exception {
        bean = new DirectServiceFacadeBean();

        try {
            bean.initialize();
            fail("DirectServiceFacadeConfigurationException should be thrown.");
        } catch (DirectServiceFacadeConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>initialize</code> method with the optional parameter is empty,
     * DirectServiceFacadeConfigurationException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testInitializeWithOptionalParamEmpty() throws Exception {
        TestHelper.setPrivateField(bean, "loggerName", "  ");

        try {
            bean.initialize();
            fail("DirectServiceFacadeConfigurationException should be thrown.");
        } catch (DirectServiceFacadeConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestReceiptData</code> method, expects the contest receipt data is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestReceiptDataAccuracy1() throws Exception {
        TCSubject tcSubject = new TCSubject(1);
        ContestReceiptData contestReceiptData = bean.getContestReceiptData(tcSubject, 1, true);
        assertNotNull("Expects the contest receipt data is returned properly.", contestReceiptData);
        assertEquals("Expects the contest receipt data is returned properly.", 1, contestReceiptData.getContestId());
        assertEquals("Expects the contest receipt data is returned properly.", "test@mydomain.com",
                contestReceiptData.getEmail());
        assertEquals("Expects the contest receipt data is returned properly.", "1234567890", contestReceiptData
                .getPhone());
        assertTrue("Expects the contest receipt data is returned properly.", contestReceiptData.isStudio());
        assertEquals("Invalid ContestId", "ARCHITECTURE - category", contestReceiptData.getContestType());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestReceiptData</code> method, expects the contest receipt data is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestReceiptDataAccuracy2() throws Exception {
        TCSubject tcSubject = new TCSubject(1);
        ContestReceiptData contestReceiptData = bean.getContestReceiptData(tcSubject, 1, false);
        assertNotNull("Expects the contest receipt data is returned properly.", contestReceiptData);
        assertEquals("Expects the contest receipt data is returned properly.", 1, contestReceiptData.getContestId());
        assertEquals("Expects the contest receipt data is returned properly.", "test@mydomain.com",
                contestReceiptData.getEmail());
        assertEquals("Expects the contest receipt data is returned properly.", "1234567890", contestReceiptData
                .getPhone());
        assertFalse("Expects the contest receipt data is returned properly.", contestReceiptData.isStudio());
        assertEquals("Invalid ContestId", "COMPONENT_DEVELOPMENT", contestReceiptData.getContestType());
    }

    /**
     * <p>
     * Failure test for the <code>getContestReceiptData</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestReceiptDataWithTcSubjectNull() throws Exception {
        try {
            bean.getContestReceiptData(null, 1, false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestReceiptData</code> method with the contestId is 0,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestReceiptDataWithContestIdZero() throws Exception {
        try {
            bean.getContestReceiptData(new TCSubject(1), 0, false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestReceiptData</code> method with the contestId is negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestReceiptDataWithContestIdNegative() throws Exception {
        try {
            bean.getContestReceiptData(new TCSubject(1), -1, false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestReceiptData</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestReceiptDataWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.getContestReceiptData(new TCSubject(1), 1, false);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestReceiptData</code> method with PersistenceException occurs,
     * DirectServiceFacadeException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestReceiptDataWithPE() throws Exception {
        try {
            bean.getContestReceiptData(new TCSubject(1), 101, false);
            fail("DirectServiceFacadeException should be thrown.");
        } catch (DirectServiceFacadeException e) {
            assertTrue(e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>sendContestReceiptByEmail</code> method, expects the contest receipt is sent
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailAccuracy1() throws Exception {
        ContestReceiptData contestReceiptData = bean.sendContestReceiptByEmail(new TCSubject(1), 1, true,
                new String[] {"test@mydomain.com"});

        assertNotNull("Expects the contest receipt is sent properly.", contestReceiptData);
        assertEquals("Expects the contest receipt is sent properly.", 1, contestReceiptData.getContestId());
        assertEquals("Expects the contest receipt is sent properly.", "test@mydomain.com", contestReceiptData
                .getEmail());
        assertEquals("Expects the contest receipt is sent properly.", "1234567890", contestReceiptData.getPhone());
        assertTrue("Expects the contest receipt is sent properly.", contestReceiptData.isStudio());
        assertEquals("Expects the contest receipt is sent properly.", "ARCHITECTURE - category", contestReceiptData
                .getContestType());
    }

    /**
     * <p>
     * Accuracy test for the <code>sendContestReceiptByEmail</code> method, expects the contest receipt is sent
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailAccuracy2() throws Exception {
        ContestReceiptData contestReceiptData = bean.sendContestReceiptByEmail(new TCSubject(1), 1, false,
                new String[] {"test@mydomain.com"});

        assertNotNull("Expects the contest receipt is sent properly.", contestReceiptData);
        assertEquals("Expects the contest receipt is sent properly.", 1, contestReceiptData.getContestId());
        assertEquals("Expects the contest receipt is sent properly.", "test@mydomain.com", contestReceiptData
                .getEmail());
        assertEquals("Expects the contest receipt is sent properly.", "1234567890", contestReceiptData.getPhone());
        assertFalse("Expects the contest receipt is sent properly.", contestReceiptData.isStudio());
        assertEquals("Expects the contest receipt is sent properly.", "COMPONENT_DEVELOPMENT", contestReceiptData
                .getContestType());
    }

    /**
     * <p>
     * Failure test for the <code>sendContestReceiptByEmail</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailWithTCSubjectNull() throws Exception {
        try {
            bean.sendContestReceiptByEmail(null, 1, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>sendContestReceiptByEmail</code> method with the contestId is 0,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailWithContestIdZero() throws Exception {
        try {
            bean.sendContestReceiptByEmail(new TCSubject(1), 0, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>sendContestReceiptByEmail</code> method with the contestId is negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailWithContestIdNegative() throws Exception {
        try {
            bean.sendContestReceiptByEmail(new TCSubject(1), -1, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>sendContestReceiptByEmail</code> method with the additionalEmailAddrs contains null
     * element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailWithAdditionalEmailAddrsContainNull() throws Exception {
        try {
            bean.sendContestReceiptByEmail(new TCSubject(1), 1, true, new String[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>sendContestReceiptByEmail</code> method with the additionalEmailAddrs contains empty
     * element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailWithAdditionalEmailAddrsContainEmpty() throws Exception {
        try {
            bean.sendContestReceiptByEmail(new TCSubject(1), 1, true, new String[] {"  "});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>sendContestReceiptByEmail</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSendContestReceiptByEmailWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.sendContestReceiptByEmail(new TCSubject(1), 1, true, new String[] {"test@mydomain.com"});
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>updateActiveContestPrize</code> method, expects the active contest prize is updated
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeAccuracy1() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestPrizes(new double[] {100, 50});
        contestPrize.setContestId(1);
        bean.updateActiveContestPrize(new TCSubject(1), contestPrize);
        // expects no error occurs
    }

    /**
     * <p>
     * Accuracy test for the <code>updateActiveContestPrize</code> method, expects the active contest prize is updated
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeAccuracy2() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setStudio(true);
        contestPrize.setContestPrizes(new double[] {100, 50});
        contestPrize.setContestId(1);
        bean.updateActiveContestPrize(new TCSubject(1), contestPrize);
        // expects no error occurs
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithTCSubjectNull() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestPrizes(new double[] {123.456});

        try {
            bean.updateActiveContestPrize(null, contestPrize);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with the contestPrize is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithContestPrizeNull() throws Exception {
        try {
            bean.updateActiveContestPrize(new TCSubject(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with the contestPrize.getContestPrizes() is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithContestPrizesNull() throws Exception {
        try {
            bean.updateActiveContestPrize(new TCSubject(1), new ContestPrize());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with the contestPrize.getContestPrizes() is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithContestPrizesEmpty() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestPrizes(new double[] {});

        try {
            bean.updateActiveContestPrize(new TCSubject(1), contestPrize);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with the contestPrize.getContestPrizes()
     * contains negative element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithContestPrizesContainNegative() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestPrizes(new double[] {-1.2});

        try {
            bean.updateActiveContestPrize(new TCSubject(1), contestPrize);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with second place prize is too low,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithSecondPrizeLow() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestPrizes(new double[] {10, 1.9});

        try {
            bean.updateActiveContestPrize(new TCSubject(1), contestPrize);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with the contestPrize.getMilestonePrizes()
     * contains negative element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithMilestonePrizesContainNegative() throws Exception {
        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setStudio(true);
        contestPrize.setContestPrizes(new double[] {10, 2});
        contestPrize.setMilestonePrizes(new double[] {-1.2});

        try {
            bean.updateActiveContestPrize(new TCSubject(1), contestPrize);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateActiveContestPrize</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateActiveContestPrizeWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        ContestPrize contestPrize = new ContestPrize();
        contestPrize.setContestPrizes(new double[] {100, 50});

        try {
            bean.updateActiveContestPrize(new TCSubject(1), contestPrize);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestPrize</code> method, expects the contest prize is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestPrizeAccuracy1() throws Exception {
        ContestPrize contestPrize = bean.getContestPrize(new TCSubject(1), 1, true);
        assertEquals("Expects the contest prize is returned properly.", 1, contestPrize.getContestId());
        assertTrue("Expects the contest prize is returned properly.", contestPrize.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestPrize</code> method, expects the contest prize is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestPrizeAccuracy2() throws Exception {
        ContestPrize contestPrize = bean.getContestPrize(new TCSubject(1), 1, false);
        assertEquals("Expects the contest prize is returned properly.", 1, contestPrize.getContestId());
        assertFalse("Expects the contest prize is returned properly.", contestPrize.isStudio());
    }

    /**
     * <p>
     * Failure test for the <code>getContestPrize</code> method with the tcSubject is null, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestPrizeWithTCSubjectNull() throws Exception {
        try {
            bean.getContestPrize(null, 1, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestPrize</code> method with the contestId is 0, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestPrizeWithContestIdZero() throws Exception {
        try {
            bean.getContestPrize(new TCSubject(1), 0, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestPrize</code> method with the contestId is negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestPrizeWithContestIdNegative() throws Exception {
        try {
            bean.getContestPrize(new TCSubject(1), -1, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestPrize</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestPrizeWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.getContestPrize(new TCSubject(1), 1, false);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestSchedule</code> method, expects the contestSchedule is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleAccuracy1() throws Exception {
        ContestSchedule contestSchedule = bean.getContestSchedule(new TCSubject(1), 1, true);
        assertEquals("Expects the contestSchedule is returned properly.", 1, contestSchedule.getContestId());
        assertTrue("Expects the contestSchedule is returned properly.", contestSchedule.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestSchedule</code> method, expects the contestSchedule is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleAccuracy2() throws Exception {
        ContestSchedule contestSchedule = bean.getContestSchedule(new TCSubject(1), 1, false);
        assertEquals("Expects the contestSchedule is returned properly.", 1, contestSchedule.getContestId());
        assertFalse("Expects the contestSchedule is returned properly.", contestSchedule.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestSchedule</code> method with PhaseType is REGISTRATION, expects the
     * contestSchedule is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleAccuracy3() throws Exception {
        ContestSchedule contestSchedule = bean.getContestSchedule(new TCSubject(1), 3, false);
        assertEquals("Expects the contestSchedule is returned properly.", 3, contestSchedule.getContestId());
        assertFalse("Expects the contestSchedule is returned properly.", contestSchedule.isStudio());
    }

    /**
     * <p>
     * Accuracy test for the <code>getContestSchedule</code> method with PhaseType is SUBMISSION, expects the
     * contestSchedule is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleAccuracy4() throws Exception {
        ContestSchedule contestSchedule = bean.getContestSchedule(new TCSubject(1), 4, false);
        assertEquals("Expects the contestSchedule is returned properly.", 4, contestSchedule.getContestId());
        assertFalse("Expects the contestSchedule is returned properly.", contestSchedule.isStudio());
    }

    /**
     * <p>
     * Failure test for the <code>getContestSchedule</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleWithTCSubjectNull() throws Exception {
        try {
            bean.getContestSchedule(null, 1, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestSchedule</code> method with the contestId is 0, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleWithContestIdZero() throws Exception {
        try {
            bean.getContestSchedule(new TCSubject(1), 0, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestSchedule</code> method with the contestId is negative,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleWithContestIdNegative() throws Exception {
        try {
            bean.getContestSchedule(new TCSubject(1), -1, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getContestSchedule</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetContestScheduleWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.getContestSchedule(new TCSubject(1), 1, false);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>extendActiveContestSchedule</code> method, expects the active contest schedule is
     * extended properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleAccuracy1() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(3000);
        bean.extendActiveContestSchedule(new TCSubject(1), extension);
        // expects no error occurs
    }

    /**
     * <p>
     * Accuracy test for the <code>extendActiveContestSchedule</code> method, expects the active contest schedule is
     * extended properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleAccuracy2() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(3000);
        extension.setStudio(true);
        bean.extendActiveContestSchedule(new TCSubject(1), extension);
        // expects no error occurs
    }

    /**
     * <p>
     * Accuracy test for the <code>extendActiveContestSchedule</code> method with PhaseType is REGISTRATION, expects
     * the active contest schedule is extended properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleAccuracy3() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(3);
        extension.setExtendMilestoneBy(1);
        extension.setExtendRegistrationBy(2);
        extension.setExtendSubmissionBy(3);
        bean.extendActiveContestSchedule(new TCSubject(1), extension);
        // expects no error occurs
    }

    /**
     * <p>
     * Accuracy test for the <code>extendActiveContestSchedule</code> method with PhaseType is SUBMISSION, expects the
     * active contest schedule is extended properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleAccuracy4() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(4);
        extension.setExtendMilestoneBy(1);
        extension.setExtendRegistrationBy(2);
        extension.setExtendSubmissionBy(3);
        bean.extendActiveContestSchedule(new TCSubject(1), extension);
        // expects no error occurs
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithTCSubjectNull() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(3000);

        try {
            bean.extendActiveContestSchedule(null, extension);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with the extension is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithExtensionNull() throws Exception {
        try {
            bean.extendActiveContestSchedule(new TCSubject(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with the extension.getContestId() <= 0,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithExtensionContestIdNotPositive() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(3000);

        try {
            bean.extendActiveContestSchedule(new TCSubject(1), extension);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with the extension.getExtendMilestoneBy()
     * is negative, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithExtendMilestoneByNegative() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(-1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(3000);

        try {
            bean.extendActiveContestSchedule(new TCSubject(1), extension);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with the
     * extension.getExtendRegistrationBy() is negative, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithExtendRegistrationByNegative() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(-2000);
        extension.setExtendSubmissionBy(3000);

        try {
            bean.extendActiveContestSchedule(new TCSubject(1), extension);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with the extension.getExtendSubmissionBy()
     * is negative, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithExtendSubmissionByNegative() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(-3000);

        try {
            bean.extendActiveContestSchedule(new TCSubject(1), extension);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(1);
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(3000);

        try {
            bean.extendActiveContestSchedule(new TCSubject(1), extension);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>extendActiveContestSchedule</code> method with phaseLength is not positive,
     * DirectServiceFacadeException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testExtendActiveContestScheduleWithPhaseLengthNotPositive() throws Exception {
        ContestScheduleExtension extension = new ContestScheduleExtension();
        extension.setContestId(3);
        extension.setExtendMilestoneBy(1000);
        extension.setExtendRegistrationBy(2000);
        extension.setExtendSubmissionBy(3000);
        try {
            bean.extendActiveContestSchedule(new TCSubject(1), extension);
            fail("DirectServiceFacadeException should be thrown.");
        } catch (DirectServiceFacadeException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>repostSoftwareContest</code> method, expects the software contest is reposted
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRepostSoftwareContestAccuracy() throws Exception {
        long result = bean.repostSoftwareContest(new TCSubject(1), 1);
        assertEquals("Expects the software contest is reposted properly.", 2, result);
    }

    /**
     * <p>
     * Failure test for the <code>repostSoftwareContest</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRepostSoftwareContestWithTCSubjectNull() throws Exception {
        try {
            bean.repostSoftwareContest(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>repostSoftwareContest</code> method with the contestId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRepostSoftwareContestWithContestIdNotPositive() throws Exception {
        try {
            bean.repostSoftwareContest(new TCSubject(1), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>repostSoftwareContest</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRepostSoftwareContestWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.repostSoftwareContest(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>createNewVersionForDesignDevContest</code> method, expects the new version contest
     * is created properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewVersionForDesignDevContestAccuracy() throws Exception {
        long result = bean.createNewVersionForDesignDevContest(new TCSubject(1), 1);
        assertEquals("Expects the new version contest is created properly.", 2, result);
    }

    /**
     * <p>
     * Failure test for the <code>createNewVersionForDesignDevContest</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewVersionForDesignDevContestWithTCSubjectNull() throws Exception {
        try {
            bean.createNewVersionForDesignDevContest(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createNewVersionForDesignDevContest</code> method with the contestId is not
     * positive, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewVersionForDesignDevContestWithContestIdNotPositive() throws Exception {
        try {
            bean.createNewVersionForDesignDevContest(new TCSubject(1), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createNewVersionForDesignDevContest</code> method with the contestServiceFacade is
     * null, IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNewVersionForDesignDevContestWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.createNewVersionForDesignDevContest(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>deleteContest</code> method, expects the contest is deleted properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteContestAccuracy1() throws Exception {
        bean.deleteContest(new TCSubject(1), 1, true);
        // expects no error occurs.
    }

    /**
     * <p>
     * Accuracy test for the <code>deleteContest</code> method, expects the contest is deleted properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteContestAccuracy2() throws Exception {
        bean.deleteContest(new TCSubject(1), 1, false);
        // expects no error occurs.
    }

    /**
     * <p>
     * Failure test for the <code>deleteContest</code> method with the tcSubject is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteContestWithTCSubjectNull() throws Exception {
        try {
            bean.deleteContest(null, 1, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>deleteContest</code> method with the contestId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteContestWithContestIdNotPositive() throws Exception {
        try {
            bean.deleteContest(new TCSubject(1), 0, true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>deleteContest</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testDeleteContestWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.deleteContest(new TCSubject(1), 1, true);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getProjectGamePlan</code> method, expects the ContestPlan list is returned
     * properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectGamePlanAccuracy() throws Exception {
        List<ContestPlan> plans = bean.getProjectGamePlan(new TCSubject(1), 1);
        assertEquals("Expects the ContestPlan list is returned properly.", 1, plans.size());
    }

    /**
     * <p>
     * Failure test for the <code>getProjectGamePlan</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectGamePlanWithTCSubjectNull() throws Exception {
        try {
            bean.getProjectGamePlan(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getProjectGamePlan</code> method with the projectId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectGamePlanWithProjectIdNotPositive() throws Exception {
        try {
            bean.getProjectGamePlan(new TCSubject(1), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getProjectGamePlan</code> method with the contestServiceFacade is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectGamePlanWithStateNull() throws Exception {
        TestHelper.setPrivateField(bean, "contestServiceFacade", null);

        try {
            bean.getProjectGamePlan(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getParentProjects</code> method, expects the parent projects is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetParentProjectsAccuracy() throws Exception {
        List<Project> projects = bean.getParentProjects(new TCSubject(1), 1);
        assertEquals("Expects the parent projects is returned properly.", 1, projects.size());
    }

    /**
     * <p>
     * Failure test for the <code>getParentProjects</code> method with the tcSubject is null, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetParentProjectsWithTCSubjectNull() throws Exception {
        try {
            bean.getParentProjects(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getParentProjects</code> method with the projectId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetParentProjectsWithProjectIdNotPositive() throws Exception {
        try {
            bean.getParentProjects(new TCSubject(1), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getParentProjects</code> method with the projectLinkManager is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetParentProjectsWithProjectLinkManagerNull() throws Exception {
        TestHelper.setPrivateField(bean, "projectLinkManager", null);

        try {
            bean.getParentProjects(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getChildProjects</code> method, expects the child projects is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetChildProjectsAccuracy() throws Exception {
        List<Project> projects = bean.getChildProjects(new TCSubject(1), 1);
        assertEquals("Expects the child projects is returned properly.", 1, projects.size());
    }

    /**
     * <p>
     * Failure test for the <code>getChildProjects</code> method with the tcSubject is null, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetChildProjectsWithTCSubjectNull() throws Exception {
        try {
            bean.getChildProjects(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getChildProjects</code> method with the projectId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetChildProjectsWithProjectIdNotPositive() throws Exception {
        try {
            bean.getChildProjects(new TCSubject(1), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getChildProjects</code> method with the projectLinkManager is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetChildProjectsWithProjectLinkManagerNull() throws Exception {
        TestHelper.setPrivateField(bean, "projectLinkManager", null);

        try {
            bean.getChildProjects(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>updateProjectLinks</code> method, expects the project links is updated properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectLinksAccuracy() throws Exception {
        bean.updateProjectLinks(new TCSubject(1), 1, new long[] {1}, new long[] {2});
        // expects no error occurs
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectLinks</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectLinksWithTCSubjectNull() throws Exception {
        try {
            bean.updateProjectLinks(null, 1, null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectLinks</code> method with the projectId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectLinksWithProjectIdNotPositive() throws Exception {
        try {
            bean.updateProjectLinks(new TCSubject(1), 0, null, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectLinks</code> method with the parentProjectIds contains not positive
     * element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectLinksWithParentProjectIdsContainNotPositive() throws Exception {
        try {
            bean.updateProjectLinks(new TCSubject(1), 1, new long[] {0}, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectLinks</code> method with the childProjectIds contains not positive
     * element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectLinksWithChildProjectIdsContainNotPositive() throws Exception {
        try {
            bean.updateProjectLinks(new TCSubject(1), 1, null, new long[] {0});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectLinks</code> method with the projectLinkManager is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectLinksWithProjectLinkManagerNull() throws Exception {
        TestHelper.setPrivateField(bean, "projectLinkManager", null);

        try {
            bean.updateProjectLinks(new TCSubject(1), 1, null, null);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getProjectBudget</code> method, expects the project budget is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectBudgetAccuracy() throws Exception {
        double budget = bean.getProjectBudget(new TCSubject(1), 1);
        assertEquals("Expects the project budget is returned properly.", 0.0, budget);
    }

    /**
     * <p>
     * Failure test for the <code>getProjectBudget</code> method with the tcSubject is null, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectBudgetWithTCSubjectNull() throws Exception {
        try {
            bean.getProjectBudget(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getProjectBudget</code> method with the billingProjectId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectBudgetWithBillingProjectIdNotPositive() throws Exception {
        try {
            bean.getProjectBudget(new TCSubject(1), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getProjectBudget</code> method with the projectDAO is null, IllegalStateException is
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetProjectBudgetWithProjectDAONull() throws Exception {
        TestHelper.setPrivateField(bean, "projectDAO", null);

        try {
            bean.getProjectBudget(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>updateProjectBudget</code> method, expects the project budget is updated properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectBudgetAccuracy() throws Exception {
        ProjectBudget result = bean.updateProjectBudget(new TCSubject(1), 1, 1);
        assertEquals("Expects the project budget is updated properly.", 1.0, result.getChangedAmount());
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectBudget</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectBudgetWithTCSubjectNull() throws Exception {
        try {
            bean.updateProjectBudget(null, 1, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectBudget</code> method with the billingProjectId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectBudgetWithBillingProjectIdNotPositive() throws Exception {
        try {
            bean.updateProjectBudget(new TCSubject(1), 0, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectBudget</code> method with the projectDAO is null, IllegalStateException
     * is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectBudgetWithProjectDAONull() throws Exception {
        TestHelper.setPrivateField(bean, "projectDAO", null);

        try {
            bean.updateProjectBudget(new TCSubject(1), 1, 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>updateProjectBudget</code> method with the emailSender is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateProjectBudgetWithEmailSenderNull() throws Exception {
        TestHelper.setPrivateField(bean, "emailSender", null);

        try {
            bean.updateProjectBudget(new TCSubject(1), 1, 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Accuracy test for the <code>getSpecReviewState</code> method, expects the specification review state is
     * returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSpecReviewStateAccuracy1() throws Exception {
        SpecReviewState state = bean.getSpecReviewState(new TCSubject(1), 1);
        assertEquals("Expects the specification review state is returned properly.",
                SpecReviewStatus.FINAL_FIX_ACCEPTED, state.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the <code>getSpecReviewState</code> method with review phase status is PhaseStatus.OPEN,
     * expects the specification review state is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSpecReviewStateAccuracy2() throws Exception {
        SpecReviewState state = bean.getSpecReviewState(new TCSubject(1), 2);
        assertEquals("Expects the specification review state is returned properly.", SpecReviewStatus.REVIEW_STARTED,
                state.getStatus());
    }

    /**
     * <p>
     * Accuracy test for the <code>getSpecReviewState</code> method with review phase status is PhaseStatus.SCHEDULED,
     * expects the specification review state is returned properly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSpecReviewStateAccuracy3() throws Exception {
        SpecReviewState state = bean.getSpecReviewState(new TCSubject(1), 3);
        assertEquals("Expects the specification review state is returned properly.",
                SpecReviewStatus.NOBODY_REGISTERED, state.getStatus());
    }

    /**
     * <p>
     * Failure test for the <code>getSpecReviewState</code> method with the tcSubject is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSpecReviewStateWithTCSubjectNull() throws Exception {
        try {
            bean.getSpecReviewState(null, 1);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getSpecReviewState</code> method with the contestId is not positive,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSpecReviewStateWithContestIdNotPositive() throws Exception {
        try {
            bean.getSpecReviewState(new TCSubject(1), 0);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getSpecReviewState</code> method with the projectManager is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSpecReviewStateWithProjectManagerNull() throws Exception {
        TestHelper.setPrivateField(bean, "projectManager", null);

        try {
            bean.getSpecReviewState(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>getSpecReviewState</code> method with the phaseManager is null,
     * IllegalStateException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSpecReviewStateWithPhaseManagerNull() throws Exception {
        TestHelper.setPrivateField(bean, "phaseManager", null);

        try {
            bean.getSpecReviewState(new TCSubject(1), 1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
