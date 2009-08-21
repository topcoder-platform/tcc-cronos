/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>
 * Demonstrates the common usage of this component.
 * </p>
 *
 * ----update in version 1.2 ------ Add new demo for
 * getMilestoneSubmissionForContest, getFinalSubmissionForContest and
 * setMilestonePrize
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.2
 */
public class Demo extends BaseTestCase {
    /**
     * <p>
     * Represents the <code>SubmissionManagerBean</code> instance.
     * </p>
     */
    private SubmissionManager manager;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        executeScriptFile("/clean.sql");
        executeScriptFile("/prepare.sql");
        executeScriptFile("/demo.sql");

        Properties env = new Properties();
        /*
         * If you need to using the JBOSS security control, please use the
         * commented codes
         */

        // env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        // env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        // env.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        // "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        env.setProperty("java.naming.factory.initial",
            "org.jnp.interfaces.NamingContextFactory");
        env.setProperty("java.naming.provider.url", "localhost:1099");

        InitialContext ctx = new InitialContext(env);

        manager = (SubmissionManager) ctx.lookup("remote/SubmissionManagerBean");
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        executeScriptFile("/clean.sql");

        super.tearDown();
    }

    /**
     * <p>
     * Demonstrates the management of submissions.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testManagement() throws Exception {
        // create prize type
        PrizeType type = new PrizeType();
        type.setPrizeTypeId(1L);

        // Create a prize and fill it
        Prize prize = new Prize();
        prize.setPlace(1);
        prize.setAmount(900.00);
        prize.setCreateDate(new Date());
        prize.setType(type);

        // and other field are omitted for clarity
        Prize createdPrize = manager.addPrize(prize);

        // Checking the ID of the prize, it would be filled. We assume ID=11
        Long prizeId = createdPrize.getPrizeId();

        // At a later point, we can retrieve and update the prize to bump the
        // prize to half.
        Prize retrievedPrize = manager.getPrize(prizeId);
        prize.setPlace(2);
        prize.setAmount(450.00);
        manager.updatePrize(retrievedPrize);

        // We can remove the prize if it no longer meets our needs.
        manager.removePrize(retrievedPrize.getPrizeId());

        // This shows basic CRUD operations for a simple entity. We can proceed
        // to more involved examples of managing a submission:

        // Suppose we have 4 submissions for a given contest (contestId=1).
        // The relevant fields are to be denoted as:
        // submission 1: submissionId=1, rank=1
        // submission 2: submissionId=2, rank=2
        // submission 3: submissionId=3, rank=3
        // submission 4: submissionId=4, rank=4

        // If we wanted to retrieve and update some properties of a submission,
        // we would do the following:
        
        /* Submission submission = manager.getSubmission(2);
         submission.setHeight(2);*/

        // this is used solving the lazy loading problem.
        /*submission.setPrizes(new HashSet < Prize >());

         manager.updateSubmission(submission);*/

        // If we wanted to retrieve the submissions, without the actual files,
        // we
        // would do the following:
        // List < Submission > submissions = manager
        // .getSubmissionsForContest(1, false);

        // This would return a list of 4 submissions shown above

        // Suppose that after additional appeals, the rankings were changed, and
        // submission 3 was promoted to rank 1. The submission result would be
        // updated:
        /* Submission submission3 = manager.getSubmission(3);
         submission.setRank(1);
         manager.updateSubmissionResult(submission3);*/

        // This would result in the rankings to be recalculated to accommodate
        // the change in ranks. The submissions for the given contest would be
        // adjusted as follows:
        // submission 3: submissionId=3, rank=1
        // submission 1: submissionId=1, rank=2
        // submission 2: submissionId=2, rank=3
        // submission 4: submissionId=4, rank=4

        // Demo updated for version 1.2
        /*
         * Suppose we have the following submissions stored currently:
         * Submission ID ID of the contest it belongs to Whether Deleted Submission Type 
         * 11 1 Y milestone_submission_type 
         * 12 1 N milestone_submission_type 
         * 13 1 N final_submission_type 
         * 14 1 N final_submission_type 
         * 15 1 N milestone_submission_type 
         * 16 2 N milestone_submission_type
         */

        // Get milestone submissions under contest 1.
        /* List < Submission > submissionList1 = manager
         .getMilestoneSubmissionsForContest(1);*/
        /*
         * submissionList1 will contain 2 elements, for submission 2 and 5.
         * Submission 1 not included since it has been deleted, submission 6
         * belongs to another contest, submission 3 and 4 are final submissions
         * rather than milestone submissions.
         */

        // Get final submissions under contest 1.
         List < Submission > submissionList2 = manager.getFinalSubmissionsForContest(1);
        /*
         * submissionList2 will contain 2 elements, for submission 3 and 4.
         * Submission 1 not included since it has been deleted, submission 6
         * belongs to another contest, submission 2 and 5 are milestone
         * submissions rather than final submissions.
         */

        /*
         * Suppose we have the following milestone prize stored: Milestone prize
         * ID maximumNumberOfSubmissionsPerContest Contests set(IDs) associated
         * with the prize Submissions set(IDs) already associated with the prize
         * 1 3 {1,3} {2}
         */

        // The following method call will not succeed:
        try {
            manager.setSubmissionMilestonePrize(12, 1); /*
             * Submission 12 already
             * associated with
             * milestone prize 1.
             */

            fail("The submission 12 already associated with mileston prize1");
        } catch (EJBException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }

        try {
            manager.setSubmissionMilestonePrize(16, 1); /*
             * Submission 16 belongs
             * to contest 2, that
             * contest doesn¡¯t exist
             * in the contests set
             * of milestone prize 1.
             */

            fail(
                "The contest of submission 16 and milestoneprize 1 are not same");
        } catch (InconsistentContestsException ie) {
            // excepted
        }

        try {
            manager.setSubmissionMilestonePrize(11, 1); /*
             * Submission 11 is
             * already deleted and
             * can¡¯t be associated
             * with the milestone
             * prize
             */

            fail("The Submistion 11 is already deleted");
        } catch (EntityNotFoundException ee) {
            // excepted
        }

        try {
            manager.setSubmissionMilestonePrize(13, 1); /*
             * Submission 3 is the
             * final submission
             * rather than milestone
             * submission
             */

            fail("Submission (13) is not the in milestion phase");
        } catch (EJBException e) {
            // excepted
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }

        try {
            manager.setSubmissionMilestonePrize(17, 1); /*
             * Submission 17 doesn¡¯t
             * exist
             */

            fail("submission 17 is not existing");
        } catch (EntityNotFoundException ee) {
            // excepted
        }

        try {
            manager.setSubmissionMilestonePrize(15, 20); /*
             * Milestone 2 doesn¡¯t
             * exist
             */

            fail("milestone prize 2 is not existing");
        } catch (EntityNotFoundException ee) {
            // excepted
        }

        // The only method call can succeed is:
        manager.setSubmissionMilestonePrize(15, 1); /*
         * That will add submission
         * 5 into the submissions
         * set of milestone prize 1
         * and save the changes.
         */

        /*
         * Please note, if the maximumNumberOfSubmissionsPerContest of milestone
         * prize 1 is 1 rather than 3, even
         * manager.setSubmissionMilestonePrize(5, 1) won¡¯t succeed since there
         * is already 1 submission(id = 2) set to that milestone prize, no any
         * more submissions can be associated if at most 1 submission can get
         * that milestone prize.
         */
    }
}
