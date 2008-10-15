/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * Demonstrates the common usage of this component.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
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

        executeScriptFile("test_files" + File.separator + "clean.sql");
        executeScriptFile("test_files" + File.separator + "prepare.sql");
        executeScriptFile("test_files" + File.separator + "demo.sql");
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
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
        executeScriptFile("test_files" + File.separator + "clean.sql");

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
        Submission submission = manager.getSubmission(2);
        submission.setHeight(2);

        // this is used solving the lazy loading problem.
        submission.setPrizes(new HashSet<Prize>());

        manager.updateSubmission(submission);

        // If we wanted to retrieve the submissions, without the actual files, we
        // would do the following:
        List<Submission> submissions = manager.getSubmissionsForContest(1, false);
        // This would return a list of 4 submissions shown above

        // Suppose that after additional appeals, the rankings were changed, and
        // submission 3 was promoted to rank 1. The submission result would be
        // updated:
        Submission submission3 = manager.getSubmission(3);
        submission.setRank(1);
        manager.updateSubmissionResult(submission3);
        // This would result in the rankings to be recalculated to accommodate
        // the change in ranks. The submissions for the given contest would be
        // adjusted as follows:
        // submission 3: submissionId=3, rank=1
        // submission 1: submissionId=1, rank=2
        // submission 2: submissionId=2, rank=3
        // submission 4: submissionId=4, rank=4
    }
}
