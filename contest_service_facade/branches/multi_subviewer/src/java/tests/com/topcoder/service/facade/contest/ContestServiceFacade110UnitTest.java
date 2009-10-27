/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean;
import com.topcoder.service.studio.submission.SubmissionManagerBean;


/**
 * The unittest cases using the mockup studioservice
 *
 * @author TCS Deveoper,
 * @version 1.1
 */
public class ContestServiceFacade110UnitTest extends TestCase {
    private ContestServiceFacade contestServiceFacade;

    /**
     * The setup method for preparing the necessary resource for testing
     *
     * @since 1.1
     */
    protected void setUp() throws Exception {
        this.contestServiceFacade = new ContestServiceFacadeBean();

        MockUpStudioServiceBean mo = new MockUpStudioServiceBean();
        java.lang.reflect.Field f = ContestServiceFacadeBean.class.getDeclaredField(
                "studioService");
        f.setAccessible(true);
        f.set(contestServiceFacade, mo);
    }

    /**
     * The method return the test suite for this class.
     *
     * @return
     * @since 1.1
     */
    public static TestSuite suite() {
        return new TestSuite(ContestServiceFacade110UnitTest.class);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#getMilestoneSubmissionsForContest(long)}</code>
     * method. Get the milestone submission for contest 1. Except to get the
     * list with 2 submissions.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testGetMilestoneSubmissionsForContest()
        throws Exception {
        /* Please refer to MockUpStudioServiceBean to find the test data */
        assertEquals(contestServiceFacade.getMilestoneSubmissionsForContest(1L)
                                         .size(), 2);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#getMilestoneSubmissionsForContest(long)}</code>
     * method. Get the milestone submission from contest 10. The
     * ContestServiceException is excepted. Since there are not a contest with
     * Id 10.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testGetMilestoneSubmissionsForContest_fail()
        throws Exception {
        try {
            contestServiceFacade.getMilestoneSubmissionsForContest(10L);
            fail("No contest with Id 10! The excpetion will be throw by MockUp");
        } catch (ContestServiceException ce) {
            // excepted;
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link SubmissionManagerBean#getFinalSubmissionsForContest(long)}</code>
     * method. Get the final submission for contest 1. Except the list with 2
     * submissions.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testGetFinalSubmissionsForContest() throws Exception {
        /* Please refer to MockUpStudioServiceBean to find the test data */
        assertEquals(contestServiceFacade.getFinalSubmissionsForContest(1L)
                                         .size(), 2);
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#getFinalSubmissionsForContest(long)}</code>
     * method. Get the final submissions for contest 10. Except the
     * ContestServiceException. Since there is not a contest with Id 10.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testGetFinalSubmissionsForContest_fail()
        throws Exception {
        try {
            contestServiceFacade.getFinalSubmissionsForContest(10L);
            fail("No contest with Id 10! The excpetion will be throw by MockUp");
        } catch (ContestServiceException ce) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. Set the submission 76 to milestonePrize 1. Except the
     * ContestServiceException. Since submission 76 is not existing.When not
     * found the submission by given conditions, throw the entity not found
     * exception.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testSetSubmissionMilestonePrize_NotFound()
        throws Exception {
        try {
            contestServiceFacade.setSubmissionMilestonePrize(76, 1);
            fail("The submission 76 does not exist");
        } catch (ContestServiceException ce) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. Set the submission 1 to milestonePrize 4. Except the
     * ContestServiceException. Since the milestonePrize 4 could not be
     * associated more submission.
     *
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testSetSubmissionMilestonePrize_Existing()
        throws Exception {
        try {
            contestServiceFacade.setSubmissionMilestonePrize(1, 4);
            fail("The milestonePrize could not be associated more submission");
        } catch (ContestServiceException ce) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. Set the submission 1 to milestonePrize 3. Except the
     * ContestServiceException. SInce the milestonePrize 3 is not existing.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testSetSubmissionMilestonePrize_fail1()
        throws Exception {
        try {
            contestServiceFacade.setSubmissionMilestonePrize(1, 3);
            fail("The milestoneprize 3 dose not exist");
        } catch (ContestServiceException ce) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. Set the submission 1 to milestonePrize 2. Except the
     * ContestServiceException. Since the type of submission 1 is not in
     * milestone.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testSetSubmissionMilestonePrize_fail2()
        throws Exception {
        try {
            contestServiceFacade.setSubmissionMilestonePrize(1, 2);
            fail("submisson is not in milestonepirze phase");
        } catch (ContestServiceException ce) {
            // excepted
        }
    }

    /**
     * <p>
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#setSubmissionMilestonePrize(long, long)}</code>
     * method. Set the submission 1 to milestonePrize 1. Not exception will be
     * occured.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @since 1.1
     */
    public void testSetSubmissionMilestonePrize_Acurracy()
        throws Exception {
        try {
            contestServiceFacade.setSubmissionMilestonePrize(1, 1);
        } catch (ContestServiceException ce) {
            fail("Occure the exception:" + ce.getMessage());
        }
    }

    /**
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#getUserContest(String)}</code>
     * method. Get the contests by username "developer1". Except the list with 2
     * contestData.
     *
     * @since 1.1
     */
    public void testGetUserContests_Acurracy1() throws Exception {
        try {
            assertEquals(contestServiceFacade.getUserContests("developer1")
                                             .size(), 2);
        } catch (ContestServiceException ce) {
            fail("Occure the exception:" + ce.getMessage());
        }
    }

    /**
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#getUserContest(String)}</code>
     * method. Get the contests by username "developer2". Except the list with 1
     * contestData.
     *
     * @since 1.1
     */
    public void testGetUserContests_Acurracy2() throws Exception {
        try {
            assertEquals(contestServiceFacade.getUserContests("developer2")
                                             .size(), 1);
        } catch (ContestServiceException ce) {
            fail("Occure the exception:" + ce.getMessage());
        }
    }

    /**
     * Unit test for
     * <code>{@link ContestServiceFacadeBean#getUserContest(String)}</code>
     * method. Get the contests by username "developer3". Except the
     * ContestServiceException thrown from mock up bean.
     *
     * @since 1.1
     */
    public void testGetUserContests_fail() throws Exception {
        try {
            contestServiceFacade.getUserContests("developer3");
            fail("The mock up method should throw the exception");
        } catch (ContestServiceException ce) {
            // excepted
        }
    }
}
